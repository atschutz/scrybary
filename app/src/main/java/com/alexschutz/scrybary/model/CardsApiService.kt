package com.alexschutz.scrybary.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CardsApiService {

    private val BASE_URL = "https://api.scryfall.com/cards/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CardsApi::class.java)

    fun getCardDetail(id: String): Single<CardDetail> {
        return api.getCardDetail(id)
    }

    fun getCardList(query: String): Single<CardListJson> {
        return api.getCardList(query)
    }
}