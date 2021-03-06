package com.alexschutz.scrybary.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alexschutz.scrybary.model.*
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CardImageViewModel(application: Application) : AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val printingsUri = MutableLiveData<String>()

    val frontUris = MutableLiveData<ArrayList<String>>()
    val backUris = MutableLiveData<ArrayList<String>>()

    fun fetchUris() {

        printingsUri.value?.let {
            disposable.add(
                cardService.getPrintings(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<CardListJson>() {

                        override fun onSuccess(cardListJson: CardListJson) {
                            val gson = GsonBuilder().create()
                            val imageJsonlist = gson.fromJson(cardListJson.data, Array<ImageJson>::class.java).toList()

                            val fronts = arrayListOf<String>()
                            val backs = arrayListOf<String>()

                            for (json in imageJsonlist) {
                                if (json.faces != null) {
                                    val faces = gson.fromJson(json.faces, Array<ImageJson>::class.java).toList()

                                    fronts.add(gson.fromJson(faces[0].imageUris, CardImage::class.java)?.imageUri ?: "")
                                    backs.add(gson.fromJson(faces[1].imageUris, CardImage::class.java)?.imageUri ?: "")
                                } else {
                                    fronts.add(gson.fromJson(json.imageUris, CardImage::class.java)?.imageUri ?: "")
                                }
                            }

                            frontUris.value = fronts
                            backUris.value = backs
                        }

                        override fun onError(e: Throwable) {

                        }
                    })
            )
        }
    }
}