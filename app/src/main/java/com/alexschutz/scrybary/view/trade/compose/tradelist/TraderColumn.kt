package com.alexschutz.scrybary.view.trade.compose.tradelist

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.view.trade.compose.printingselector.CardCondition
import com.alexschutz.scrybary.view.trade.compose.testCard
import com.alexschutz.scrybary.view.trade.compose.testCards

@Composable
fun TraderColumn(
    modifier: Modifier,
    list: List<CardTradeInfo>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp)
    ) {
        items(list) {
            val price =
                if (it.isFoil) it.cardSet.prices?.usdFoil ?: 0.00
                else it.cardSet.prices?.usd ?: 0.00

            TraderListItem(
                it.id,
                it.name,
                it.cardSet.symbol ?: "",
                it.isFoil,
                "$price"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeColumnPreview() {
    TraderColumn(Modifier, listOf())
}

