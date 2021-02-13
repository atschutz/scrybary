package com.alexschutz.scrybary.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class Card(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("mana_cost")
    val cmc: String,

    @SerializedName("type_line")
    val type: String,

    @SerializedName("power")
    val power: String?,

    @SerializedName("toughness")
    val toughness: String?
)

data class CardDetail(
    @SerializedName("oracle_text")
    val oracleText: String?,

    @SerializedName("rulings_uri")
    val rulingsUri: String,

    @SerializedName("image_uris")
    val ImageUris: JsonArray
)

data class CardListJson(
    @SerializedName("data")
    val data: JsonArray
)