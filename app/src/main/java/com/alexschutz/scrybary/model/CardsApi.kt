package com.alexschutz.scrybary.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsApi {
    @GET
    fun getCard(id: String): Single<Card>

    @GET("search")
    fun getCardList(@Query("q") query: String): Single<CardListJson>
}