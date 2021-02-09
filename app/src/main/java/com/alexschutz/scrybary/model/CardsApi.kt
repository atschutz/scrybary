package com.alexschutz.scrybary.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsApi {
    @GET("cards/search")
    fun getCards(query: String): Single<List<Card>>
}