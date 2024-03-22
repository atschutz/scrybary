package com.alexschutz.scrybary.trade.model

import android.util.Log
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardData
import com.alexschutz.scrybary.model.CardImage
import com.alexschutz.scrybary.model.CardListJson
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.ImageJson
import com.alexschutz.scrybary.model.PriceData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import java.util.*
import javax.inject.Inject

class TradeRepository @Inject constructor(
    private val cardService: TradeWebService
) {
    suspend fun fetchFromRemoteWithSearch(search: String): List<Card> =
        if (search.length >= 3) {
            val response = cardService.getCardList(search)

            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val list = gson.fromJson(response.body()?.data, Array<Card>::class.java)
                    .toMutableList()

                // Sort list so perfect matches are at the top
                if (list.size > 1) list.let {

                    val regex = Regex("[^A-Za-z0-9]")

                    val scrubbedSearch =
                        regex.replace(search, "")

                    for (card in list) {

                        val scrubbedCardName = regex.replace(card.name, "")

                        if (scrubbedCardName.equals(scrubbedSearch, true)) {
                            list.remove(card)
                            list.add(0, card)

                            break
                        }
                    }
                }
                list
            } else {
                listOf()
            }
        } else listOf()

    suspend fun fetchCardSets(id: String): List<CardData> {
        val cardDetail = cardService.getCardDetail(id)

        if (cardDetail.isSuccessful) {
            val printings = cardService.getPrintings(cardDetail.body()?.printUri ?: "")

            if (printings.isSuccessful) printings.body()?.let {
                return setPrintings(it)
            }
            else Log.e("Error receiving card printings: ", printings.errorBody().toString())
        } else {
            Log.e("Error receiving card sets: ", cardDetail.errorBody().toString())
        }
        return listOf()
    }

    private fun setPrintings(cardListJson: CardListJson): List<CardData> {
        val gson = GsonBuilder().create()
        val imageJsonList = gson.fromJson(
            cardListJson.data ?: JsonArray(),
            Array<ImageJson>::class.java
        ).toList()

        val cards = mutableListOf<CardData>()

        for (json in imageJsonList) {
            val prices = gson.fromJson(json.prices, PriceData::class.java)

            // Only include cards with physical printings for now.
            if (prices.usdFoil != null || prices.usd != null) {
                val faces = json.faces?.let {
                    gson.fromJson(it, Array<ImageJson>::class.java).toList()
                }

                val imageUri = gson.fromJson(
                    faces?.let { it[0].imageUris } ?: json.imageUris,
                    CardImage::class.java
                )?.imageUri ?: ""

                cards.add(
                    CardData(
                        CardSet(
                            json.setName,
                            json.symbol.toString().uppercase(Locale.getDefault()),
                            imageUri,
                            prices
                        ),
                        cards.size,
                    )
                )
            }
        }

        return cards
    }
}