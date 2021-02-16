package com.alexschutz.scrybary.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardDetail
import com.alexschutz.scrybary.model.CardImage
import com.alexschutz.scrybary.model.CardsApiService
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val cardService = CardsApiService()
    private val disposable = CompositeDisposable()

    val card = MutableLiveData<Card>()
    val cardDetail = MutableLiveData<CardDetail>()
    val cardImageUri = MutableLiveData<String>()

    fun fetchCardDetail() {

        // If card's value isn't null, set search to the id. otherwise set search to empty string.
        val search = if (card.value != null) card.value!!.id else ""

        disposable.add(
            cardService.getCardDetail(search)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<CardDetail>() {

                    override fun onSuccess(detail: CardDetail) {

                        // Assign cardDetail to returned detail.
                        cardDetail.value = detail

                        // Get single image URI from Json field imageUris in CardDetail.
                        val gson = GsonBuilder().create()
                        val imageGson = gson.fromJson(detail.imageUris, CardImage::class.java)

                        // Assign URI to cardImageUri string.
                        cardImageUri.value = imageGson.imageUri

                        Toast.makeText(getApplication(), imageGson.imageUri, Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {

                        e.printStackTrace()
                        Toast.makeText(getApplication(), "It did not work: $e", Toast.LENGTH_LONG).show()
                    }
                })
        )
    }
}