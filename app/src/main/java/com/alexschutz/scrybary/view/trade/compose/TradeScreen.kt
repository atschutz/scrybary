package com.alexschutz.scrybary.view.trade.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TradeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TraderColumn(modifier = Modifier.weight(1f))
        TradeListMiddleBar()
        TraderColumn(modifier = Modifier.weight(1f))
        TradeListBottomBar()
    }
}

@Preview(showBackground = true)
@Composable
fun TradeScreenPreview() {
    TradeScreen()
}