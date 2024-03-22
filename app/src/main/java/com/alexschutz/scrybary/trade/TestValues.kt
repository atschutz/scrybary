package com.alexschutz.scrybary.trade

import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardTradeInfo

val testCard = Card(
    id = "123456",
    name = "Tarmogoyf",
    cmc = "{1}{G}",
    type = "Creature - Lhurgoyf",
    power = "*",
    toughness = "1+*",
    loyalty = null,
)

val testCards = listOf(
    testCard,
    testCard,
    testCard,
    testCard,
    testCard,
    testCard,
    testCard,
    testCard,
    testCard,
)

val testCardSet = CardSet(
    set = "Modern Masters 3",
    symbol = "MM3",
    imageUri = "",
)

val testCardTradeInfo = CardTradeInfo(
    "1234",
    "Tarmogoyf",
    testCardSet,
    true,
)

val listTestCardInfo = listOf(
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
    testCardTradeInfo,
)