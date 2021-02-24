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

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val card = MutableLiveData<Card>()
    val cardDetail = MutableLiveData<CardDetail>()
    val cardImageUri = MutableLiveData<String>()
    val legalities = MutableLiveData<ArrayList<Legality>>()
    val rulings = MutableLiveData<List<Ruling>>()

    fun fetchCardDetail() {

        // If card's value isn't null, set search to the id. otherwise set search to empty string.
        card.value?.let{
            disposable.add(
                cardService.getCardDetail(it.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<CardDetail>() {

                        override fun onSuccess(detail: CardDetail) {

                            // TODO app does not get transform card info from API because info is stored in card_faces.
                            // TODO get card face list, if property exists, get info from card face.

                            // Assign cardDetail to returned detail.
                            cardDetail.value = detail

                            val gson = GsonBuilder().create()

                            // Get single image URI from Json field imageUris in CardDetail.
                            val imageGson = gson.fromJson(detail.imageUris, CardImage::class.java)
                            cardImageUri.value = imageGson?.imageUri ?: ""

                            // Since Scryfall keeps its legalities in a json with a property representing
                            // each format, and we want to future proof, we have to parse the json into
                            // a map, and then convert it into a list of Legality objects.
                            val legalityList = arrayListOf<Legality>()
                            for ((key, value) in gson.fromJson(detail.legalities, Map::class.java)) {

                                // Format fields to look nice.
                                val format = key.toString().capitalize(Locale.getDefault())
                                val legality = value.toString().replace("_", " ").toUpperCase(Locale.getDefault())

                                legalityList.add(Legality(format, legality))
                            }
                            legalities.value = legalityList
                        }

                        override fun onError(e: Throwable) {

                            e.printStackTrace()
                            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show()
                        }
                    })
            )
        }
    }

    fun fetchCardRulings() {

        card.value?.let{
            disposable.add(
                cardService.getCardRulings(it.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<RulingListJson>() {

                        override fun onSuccess(rulingList: RulingListJson) {

                            rulings.value = GsonBuilder().create()
                                .fromJson(rulingList.data, Array<Ruling>::class.java).toList()
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show()
                        }

                    })
            )
        }
    }
}