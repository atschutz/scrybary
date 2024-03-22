package com.alexschutz.scrybary.trade.tradelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexschutz.scrybary.model.CardTradeInfo

@Composable
fun TraderView(
    modifier: Modifier,
    list: List<CardTradeInfo>,
    isListMode: Boolean,
) {
    if (isListMode) TraderColumn(modifier = modifier, list = list)
    else TraderGrid(modifier = modifier, list = list)
}