package com.alexschutz.scrybary.view.trade.compose.printingselector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alexschutz.scrybary.model.CardData
import com.alexschutz.scrybary.model.CardDetail
import com.alexschutz.scrybary.model.CardImage
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardsApiService
import com.alexschutz.scrybary.model.ImageJson
import com.alexschutz.scrybary.model.PriceData
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class PrintingSelectorViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val name: String = checkNotNull(savedStateHandle["card_name"])
    val cardId: String = checkNotNull(savedStateHandle["card_id"])

    var data: List<CardData> by mutableStateOf(listOf())

    // Currently focused card. Blank card if empty data.
    var currentCard: CardData by mutableStateOf(BLANK_CARD)

    var isFoil: Boolean by mutableStateOf(false)

    init {
        fetchCardSets(cardId)
    }

    // TODO Move to a repository.
    private fun fetchCardSets(id: String) {
        // TODO Un-nest with flatMap.
        disposable.add(
            cardService.getCardDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CardDetail>() {
                    override fun onSuccess(detail: CardDetail) {
                        detail.printUri?.let {
                            cardService.getPrintings(it)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<CardListJson>() {
                                    override fun onSuccess(cardListJson: CardListJson) {
                                        setPrintings(cardListJson)
                                    }

                                    override fun onError(e: Throwable) {
                                        e.printStackTrace()
                                    }
                                })
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                }
            )
        )
    }

    private fun setPrintings(cardListJson: CardListJson) {
        val gson = GsonBuilder().create()
        val imageJsonList = gson.fromJson(cardListJson.data, Array<ImageJson>::class.java).toList()

        val cards = mutableListOf<CardData>()

        for (json in imageJsonList) {
            val prices = gson.fromJson(json.prices, PriceData::class.java)

            // Only include cards with physical printings for now.
            if (prices.usdFoil != null || prices.usd != null) {
                val faces = json.faces?.let {
                    gson.fromJson(it, Array<ImageJson>::class.java).toList()
                }

                val imageUri = gson.fromJson(
                    faces?.let { it[0].imageUris } ?: json.imageUris,
                    CardImage::class.java
                )?.imageUri ?: ""

                cards.add(
                    CardData(
                        CardSet(
                            json.setName,
                            json.symbol.toString().uppercase(Locale.getDefault()),
                            imageUri,
                            prices
                        ),
                        cards.size,
                    )
                )
            }
        }

        data = cards
        currentCard = if (data.isNotEmpty()) data.first() else BLANK_CARD
        isFoil = currentCard.startFoil
    }

    companion object {
        val BLANK_CARD =
            CardData(
                CardSet(
                    set = null,
                    symbol = null,
                    imageUri = null,
                ),
                index = -1
            )
    }
}