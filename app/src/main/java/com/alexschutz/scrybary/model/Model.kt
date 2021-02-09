package com.alexschutz.scrybary.model

import com.google.gson.annotations.SerializedName

data class Card(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("cmc")
    val cmc: Double,

    @SerializedName("type_line")
    val type: String,

    @SerializedName("power")
    val power: String?,

    @SerializedName("toughness")
    val toughness: String?
)