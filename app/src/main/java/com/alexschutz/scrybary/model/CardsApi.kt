package com.alexschutz.scrybary.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardsApi {
    @GET("{id}")
    fun getCardDetail(@Path("id") id: String): Single<CardDetail>

    @GET("search")
    fun getCardList(@Query("q") query: String): Single<CardListJson>
}