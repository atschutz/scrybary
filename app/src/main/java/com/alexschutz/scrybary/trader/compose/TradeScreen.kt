package com.alexschutz.scrybary.trader.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TradeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TradeListTopBar()
        Column(modifier = Modifier.fillMaxSize()) {
            TraderColumn(modifier = Modifier.weight(1f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            TraderColumn(
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeScreenPreview() {
    TradeScreen()
}