package com.alexschutz.scrybary.view.trade.compose

import com.alexschutz.scrybary.model.Card

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