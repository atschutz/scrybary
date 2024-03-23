package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.alexschutz.scrybary.model.CardTradeInfo

@Composable
fun TraderView(
    modifier: Modifier,
    list: List<CardTradeInfo>,
    isListMode: Boolean,
    margin: Dp,
    isTop: Boolean,
    onListItemClicked: (card: CardTradeInfo) -> Unit
) {
    if (isListMode) TraderColumn(
        modifier = modifier,
        list = list,
        margin = margin,
        isTop = isTop,
        onListItemClicked = onListItemClicked
    ) else TraderGrid(
        modifier = modifier,
        list = list,
        margin = margin,
        isTop = isTop,
        onListItemClicked = onListItemClicked
    )
}