package com.alexschutz.scrybary.model

import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CardSearchRepository {
    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    fun fetchFromRemote(search: String): List<Card> {
        var list = mutableListOf<Card>()
        if (search.length >= 3) {
            disposable.add(
                cardService.getCardList(search)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<CardListJson>() {
                        override fun onSuccess(cardListJson: CardListJson) {
                            Log.d("-as-", "success!")
                            val gson = GsonBuilder().create()
                            list = gson.fromJson(cardListJson.data, Array<Card>::class.java)
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
                            Log.d("-as-", "cards were updated: $list")
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            Log.d("-as-", "error! ${e.printStackTrace()}")
                            // Clear card list.
                            list = mutableListOf()
                        }
                    })
            )
        }
        Log.d("-as-", "list outside: $list")
        return list
    }

    companion object {
        @Volatile
        private var instance: CardSearchRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: CardSearchRepository().also {  instance = it }
        }
    }
}