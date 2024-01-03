package com.alexschutz.scrybary.view.trade.compose.printingselector

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
import retrofit2.Response
import java.security.PrivateKey
import java.util.*

class PrintingSelectorViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val cardName: String = checkNotNull(savedStateHandle["card_name"])

    var cardSets: List<CardSet> by mutableStateOf(listOf())

    var currentPrinting: CardSet by mutableStateOf(
        CardSet(
            "", "", "",
            PriceData(0.00f, 0.00f)
        )
    )

    init {
        val cardId: String = checkNotNull(savedStateHandle["card_id"])
        fetchCardSets(cardId)
    }

    private fun fetchCardSets(id: String) {
        Log.d("-as-", "fetching $id...")
        // ID -> Printings -> Image URIs, Sets, and Prices.
        disposable.add(
            cardService.getCardDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CardDetail>() {
                    override fun onSuccess(detail: CardDetail) {
                        Log.d("-as-", "card detail fetched! URI: ${detail.printUri}")
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
                                        Log.d("-as-", "error fetching printings: $e")
                                    }
                                })
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Log.d("-as-", "error fetching card detail: $e")
                    }
                }
            )
        )
    }

    private fun setPrintings(cardListJson: CardListJson) {
        val gson = GsonBuilder().create()
        val imageJsonList = gson.fromJson(cardListJson.data, Array<ImageJson>::class.java).toList()

        val cards = mutableListOf<CardSet>()

        for (json in imageJsonList) {
            val faces = json.faces?.let { gson.fromJson(it, Array<ImageJson>::class.java).toList() }

            cards.add(
                CardSet(
                    json.setName,
                    json.symbol.toString().uppercase(Locale.getDefault()),
                    gson.fromJson(
                        faces?.let { it[0].imageUris } ?: json.imageUris,
                        CardImage::class.java
                    )?.imageUri ?: "",
                    gson.fromJson(json.prices, PriceData::class.java)
                )
            )
        }

        cardSets = cards
        if (cardSets.isNotEmpty()) { currentPrinting = cardSets[0] }

        Log.d("-as-", "printings fetched! $cardSets")
    }
}