package com.alexschutz.scrybary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.CardsApiService
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class LibraryViewModel(application: Application): AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val search = MutableLiveData<String>()
    val cards = MutableLiveData<List<Card>>()

    val loading = MutableLiveData<Boolean>()

    val hasNoResults = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()

    fun fetchFromRemote() {

        loading.value = true

        hasNoResults.value = false
        hasError.value = false

        if (search.value?.length ?: 0 >= 3) {

            disposable.add(
                cardService.getCardList(search.value ?: "")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<CardListJson>() {

                        override fun onSuccess(cardListJson: CardListJson) {

                            val gson = GsonBuilder().create()
                            val list = gson.fromJson(cardListJson.data, Array<Card>::class.java).toMutableList()

                            // Sort list so perfect matches are at the top
                            if (list.size > 1) list.let {

                                val regex = Regex("[^A-Za-z0-9]")

                                val scrubbedSearch =
                                    regex.replace(search.value ?: "", "")

                                for (card in list) {

                                    val scrubbedCardName = regex.replace(card.name, "")

                                    if (scrubbedCardName.equals(scrubbedSearch, true)) {
                                        list.remove(card)
                                        list.add(0, card)

                                        break
                                    }
                                }
                            }

                            cards.value = list
                            loading.value = false
                        }

                        override fun onError(e: Throwable) {

                            e.printStackTrace()

                            // Clear card list.
                            cards.value = listOf()

                            if (e.message.toString().contains("404")) hasNoResults.value = true
                            else hasError.value = true

                            loading.value = false
                        }
                    })
            )
        } else {

            loading.value = false
            hasNoResults.value = true
        }
    }
}

