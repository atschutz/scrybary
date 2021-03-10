package com.alexschutz.scrybary.viewmodel

import android.app.Application
import android.widget.Toast
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
                            cards.value = gson.fromJson(cardListJson.data, Array<Card>::class.java).toList()

                            loading.value = false
                        }

                        override fun onError(e: Throwable) {

                            e.printStackTrace()

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

