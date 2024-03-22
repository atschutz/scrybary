package com.alexschutz.scrybary.trade.model

import com.alexschutz.scrybary.model.CardDetail
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.RulingListJson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TradeWebService @Inject constructor() {
    private val BASE_URL = "https://api.scryfall.com/cards/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TradeApi::class.java)

    suspend fun getCardDetail(id: String): Response<CardDetail> = api.getCardDetail(id)

    suspend fun getCardList(query: String): Response<CardListJson> = api.getCardList(query)

    suspend fun getCardRulings(id: String): Response<RulingListJson> = api.getCardRulings(id)

    suspend fun getPrintings(url: String): Response<CardListJson> = api.getPrintings(url)
}