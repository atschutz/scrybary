package com.alexschutz.scrybary.model

import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
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
) : Parcelable

data class CardDetail(

    @SerializedName("oracle_text")
    val oracleText: String?,

    @SerializedName("legalities")
    val legalities: JsonObject,

    @SerializedName("rulings_uri")
    val rulingsUri: String,

    @SerializedName("image_uris")
    val imageUris: JsonObject,
)

data class CardImage(
    @SerializedName("normal")
    val imageUri: String
)

data class CardListJson(
    @SerializedName("data")
    val data: JsonArray
)

data class Ruling(
    @SerializedName("source")
    val source: String,

    @SerializedName("published_at")
    val publishDate: Date,

    @SerializedName("comment")
    val comment: String
)

data class RulingListJson(
    @SerializedName("data")
    val data: JsonArray
)

data class LegalityListJson(
    @SerializedName("legalities")
    val legalities: JsonObject
)

data class Legality(
    val format: String,
    val state: String
)

