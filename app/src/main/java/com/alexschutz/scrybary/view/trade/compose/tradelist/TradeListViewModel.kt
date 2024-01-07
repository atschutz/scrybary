package com.alexschutz.scrybary.view.trade.compose.tradelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardData
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.model.CardsApiService
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TradeListViewModel: ViewModel() {
    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    var searchListCards: List<Card> by mutableStateOf(listOf())

    var p1List: List<CardTradeInfo> by mutableStateOf(listOf())
    var p2List: List<CardTradeInfo> by mutableStateOf(listOf())

    fun fetchFromRemoteWithSearch(search: String) {
        if (search.length >= 3) {
            disposable.add(
                cardService.getCardList(search)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<CardListJson>() {
                        override fun onSuccess(cardListJson: CardListJson) {
                            val gson = GsonBuilder().create()
                            val list = gson.fromJson(cardListJson.data, Array<Card>::class.java)
                                .toMutableList()

                            // Sort list so perfect matches are at the top
                            if (list.size > 1) list.let {

                                val regex = Regex("[^A-Za-z0-9]")

                                val scrubbedSearch =
                                    regex.replace(search, "")

                                for (card in list) {

                                    val scrubbedCardName = regex.replace(card.name, "")

                                    if (scrubbedCardName.equals(scrubbedSearch, true)) {
                                        list.remove(card)
                                        list.add(0, card)

                                        break
                                    }
                                }
                            }
                            searchListCards = list
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            // Clear card list.
                            searchListCards = mutableListOf()
                        }
                    })
            )
        }
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