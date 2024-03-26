package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.toDollars

@Composable
fun TraderColumn(
    modifier: Modifier,
    list: List<CardTradeInfo>,
    margin: Dp,
    isTop: Boolean,
    onListItemClicked: (card: CardTradeInfo) -> Unit,
) {
    var selectedCard by remember { mutableStateOf<CardTradeInfo?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxHeight()
    ) {
        if (!isTop) { item { Box(modifier = Modifier.height(margin)) }}
        items(list) {
            val price =
                if (it.isFoil) it.cardSet.prices?.usdFoil?.toDollars() ?: "$0.00"
                else it.cardSet.prices?.usd?.toDollars() ?: "$0.00"

            TraderListItem(
                it.name,
                it.cardSet.symbol ?: "",
                Modifier.clickable {
                    selectedCard = if (selectedCard == it) null else it
                    onListItemClicked(it)
                },
                it.isFoil,
                price,
                it == selectedCard
            )
        }
        if (isTop) { item { Box(modifier = Modifier.height(margin)) }}
    }
}

@Preview(showBackground = true)
@Composable
fun TradeColumnPreview() {
    TraderColumn(Modifier, listOf(), 0.dp, true) { }
}

