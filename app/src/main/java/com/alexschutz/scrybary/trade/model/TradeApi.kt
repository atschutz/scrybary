package com.alexschutz.scrybary.trade.model

import com.alexschutz.scrybary.model.CardDetail
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.RulingListJson
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface TradeApi {
    @GET("{id}")
    suspend fun getCardDetail(@Path("id") id: String): Response<CardDetail>

    @GET("search")
    suspend fun getCardList(@Query("q") query: String): Response<CardListJson>

    @GET("{id}/rulings")
    suspend fun getCardRulings(@Path("id") id: String): Response<RulingListJson>

    @GET
    suspend fun getPrintings(@Url url: String?): Response<CardListJson>
}