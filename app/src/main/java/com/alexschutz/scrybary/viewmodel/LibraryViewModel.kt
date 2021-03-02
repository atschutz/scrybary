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
    // TODO implement loading bar

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    private val search = MutableLiveData<String>()
    private val cards = MutableLiveData<List<Card>>()

    private val loading = MutableLiveData<Boolean>()

    fun fetchFromRemote() {

        loading.value = true

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
                        Toast.makeText(getApplication(), "It did not work: $e", Toast.LENGTH_LONG).show()
                    }
                })
        )
    }
}