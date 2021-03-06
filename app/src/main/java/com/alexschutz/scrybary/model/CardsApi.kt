package com.alexschutz.scrybary.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface CardsApi {
    @GET("{id}")
    fun getCardDetail(@Path("id") id: String): Single<CardDetail>

    @GET("search")
    fun getCardList(@Query("q") query: String): Single<CardListJson>

    @GET("{id}/rulings")
    fun getCardRulings(@Path("id") id: String): Single<RulingListJson>

    @GET
    fun getPrintings(@Url url: String?): Single<CardListJson>
}