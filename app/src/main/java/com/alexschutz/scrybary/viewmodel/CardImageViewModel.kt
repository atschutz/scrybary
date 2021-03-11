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
import java.util.*
import kotlin.collections.ArrayList

class CardImageViewModel(application: Application) : AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val printingsUri = MutableLiveData<String>()

    val frontUris = MutableLiveData<ArrayList<String>>()
    val backUris = MutableLiveData<ArrayList<String>>()

    val frontSets = MutableLiveData<ArrayList<CardSet>>()
    val backSets = MutableLiveData<ArrayList<CardSet>>()

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

                            val fronts = arrayListOf<CardSet>()
                            val backs = arrayListOf<CardSet>()

                            for (json in imageJsonlist) {
                                if (json.faces != null) {

                                    val faces = gson.fromJson(json.faces, Array<ImageJson>::class.java).toList()

                                    fronts.add(
                                        CardSet(
                                            json.setName,
                                            json.symbol.toString().toUpperCase(Locale.getDefault()),
                                            gson.fromJson(faces[0].imageUris, CardImage::class.java)?.imageUri ?: ""
                                        )
                                    )

                                    backs.add(
                                        CardSet(
                                            json.setName,
                                            json.symbol.toString().toUpperCase(Locale.getDefault()),
                                            gson.fromJson(faces[1].imageUris, CardImage::class.java)?.imageUri ?: ""
                                        )
                                    )
                                } else {
                                    fronts.add(
                                        CardSet(
                                            json.setName,
                                            json.symbol.toString().toUpperCase(Locale.getDefault()),
                                            gson.fromJson(json.imageUris, CardImage::class.java)?.imageUri ?: ""
                                        )
                                    )
                                }
                            }

                            frontSets.value = fronts
                            backSets.value = backs
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
            )
        }
    }
}