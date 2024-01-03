package com.alexschutz.scrybary.view.trade.compose

import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.view.trade.compose.printingselector.CardCondition

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
    testCard,
    testCardSet,
    CardCondition.NEAR_MINT,
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