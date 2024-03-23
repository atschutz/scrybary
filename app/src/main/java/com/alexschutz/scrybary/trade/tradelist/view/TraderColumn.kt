package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp)
    ) {
        if (!isTop) { item { Box(modifier = Modifier.height(margin)) }}
        items(list) {
            val price =
                if (it.isFoil) it.cardSet.prices?.usdFoil?.toDollars() ?: "$0.00"
                else it.cardSet.prices?.usd?.toDollars() ?: "$0.00"

            TraderListItem(
                it.id,
                it.name,
                it.cardSet.symbol ?: "",
                it.isFoil,
                price
            )
        }
        if (isTop) { item { Box(modifier = Modifier.height(margin)) }}
    }
}

@Preview(showBackground = true)
@Composable
fun TradeColumnPreview() {
    TraderColumn(Modifier, listOf(), 0.dp, true)
}

