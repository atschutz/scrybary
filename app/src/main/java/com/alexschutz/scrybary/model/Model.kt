package com.alexschutz.scrybary.model

import android.os.Parcelable
import com.alexschutz.scrybary.view.trade.compose.conditionandset.CardCondition
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
    val cmc: String?,

    @SerializedName("type_line")
    val type: String,

    @SerializedName("power")
    val power: String?,

    @SerializedName("toughness")
    val toughness: String?,

    @SerializedName("loyalty")
    val loyalty: String?,
) : Parcelable

data class CardDetail(

    @SerializedName("oracle_text")
    val oracleText: String?,

    @SerializedName("legalities")
    val legalities: JsonObject?,

    @SerializedName("rulings_uri")
    val rulingsUri: String?,

    @SerializedName("flavor_text")
    val flavor: String?,

    @SerializedName("image_uris")
    val imageUris: JsonObject?,

    @SerializedName("prints_search_uri")
    val printUri: String?,

    @SerializedName("card_faces")
    val faces: JsonArray?
)

// TODO see if there's a way to work with card faces that
//  doesn't involve reassigning all these values.
//  Can probably merge Card, CardFace, and CardDetail.
data class CardFace(

    @SerializedName("name")
    val name: String?,

    @SerializedName("mana_cost")
    val cmc: String?,

    @SerializedName("type_line")
    val type: String?,

    @SerializedName("power")
    val power: String?,

    @SerializedName("toughness")
    val toughness: String?,

    @SerializedName("loyalty")
    val loyalty: String?,

    @SerializedName("oracle_text")
    val oracleText: String?,

    @SerializedName("flavor_text")
    val flavor: String?,

    @SerializedName("image_uris")
    val imageUris: JsonObject?,
)

data class CardImage(
    @SerializedName("border_crop")
    val imageUri: String?
)

data class ImageJson(
    @SerializedName("image_uris")
    val imageUris: JsonObject?,

    @SerializedName("card_faces")
    val faces: JsonArray?,

    @SerializedName("set")
    val symbol: String?,

    @SerializedName("set_name")
    val setName: String?,

    @SerializedName("prices")
    val prices: JsonObject?,
)

data class CardListJson(
    @SerializedName("data")
    val data: JsonArray?
)

data class Ruling(
    @SerializedName("source")
    val source: String?,

    @SerializedName("published_at")
    val publishDate: Date?,

    @SerializedName("comment")
    val comment: String?
)

data class RulingListJson(
    @SerializedName("data")
    val data: JsonArray?
)

data class Legality(
    val format: String?,
    val state: String?
)

data class CardSet(
    val set: String?,
    val symbol: String?,
    val imageUri: String?,
    val prices: PriceData,
)

data class CardTradeInfo(
    val card: Card,
    val cardSet: CardSet,
    val condition: CardCondition,
    val isFoil: Boolean = false,
)

data class PriceData(
    val usd: Float?,
    val usdFoil: Float?,
)

