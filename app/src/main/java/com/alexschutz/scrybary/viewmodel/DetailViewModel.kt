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

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val card = MutableLiveData<Card>()

    val cardFront = MutableLiveData<CardFace>()
    val cardBack = MutableLiveData<CardFace>()

    // TODO merge these.
    val cardFrontImageUri = MutableLiveData<String>()
    val cardBackImageUri = MutableLiveData<String>()

    val printUri = MutableLiveData<String>()

    val legalities = MutableLiveData<ArrayList<Legality>>()
    val rulings = MutableLiveData<List<Ruling>>()

    val loading = MutableLiveData<Boolean>()

    fun fetchCardDetail() {

        loading.value = true

        // If card's value isn't null, set search to the id. otherwise set search to empty string.
        card.value?.let {
            disposable.add(
                cardService.getCardDetail(it.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<CardDetail>() {

                        override fun onSuccess(detail: CardDetail) {

                            // Assign values to front and back card faces.
                            configureCardFaces(it, detail)

                            // Get printing Uri to pass to CardImage.
                            printUri.value = detail.printUri

                            // Since Scryfall keeps its legalities in a json with a property representing
                            // each format, and we want to future proof, we have to parse the json into
                            // a map, and then convert it into a list of Legality objects.
                            val legalityList = arrayListOf<Legality>()
                            for ((key, value) in GsonBuilder().create()
                                .fromJson(detail.legalities, Map::class.java)) {

                                // Format fields to look nice.
                                val format = key.toString().capitalize(Locale.getDefault())
                                val legality = value.toString().replace("_", " ")
                                    .toUpperCase(Locale.getDefault())

                                legalityList.add(Legality(format, legality))
                            }
                            legalities.value = legalityList

                            fetchCardRulings()
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

        card.value?.let {
            disposable.add(
                cardService.getCardRulings(it.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<RulingListJson>() {

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

        loading.value = false
    }

    fun configureCardFaces(card: Card, detail: CardDetail) {

        cardFront.value = null
        cardBack.value = null

        val gson = GsonBuilder().create()

        if (detail.faces == null) {

            // If we don't have faces, it means the card does not transform.
            // This means we can assign the card face members from the CardItem and CardDetails.
            cardFront.value = CardFace(
                card.name,
                card.cmc,
                card.type,
                card.power,
                card.toughness,
                card.loyalty,
                detail.oracleText,
                detail.flavor,
                detail.imageUris
            )

            // Get single image URI from Json field imageUris in CardDetail.
            cardFrontImageUri.value =
                gson.fromJson(detail.imageUris, CardImage::class.java)?.imageUri ?: ""
        } else {

            // Convert faces Json array to CardFace array then assign them to front and back.
            val faces = gson.fromJson(detail.faces, Array<CardFace>::class.java).toList()
            cardFront.value = faces[0]
            cardBack.value = faces[1]

            // Set front and back image URIs
            cardFrontImageUri.value =
                gson.fromJson(cardFront.value?.imageUris, CardImage::class.java)?.imageUri ?: ""

            cardBackImageUri.value =
                gson.fromJson(cardBack.value?.imageUris, CardImage::class.java)?.imageUri ?: ""
        }
    }
}