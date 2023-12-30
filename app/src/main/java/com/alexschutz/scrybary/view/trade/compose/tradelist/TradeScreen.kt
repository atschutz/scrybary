package com.alexschutz.scrybary.view.trade.compose.tradelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.view.trade.compose.testCards

@Composable
fun TradeScreen(onNavigate: (Int) -> Unit) {

    Box (modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TradeListTopBar(onNavigate)
            TraderColumn(modifier = Modifier.weight(1f))
            TradeListMiddleBar()
            TraderColumn(modifier = Modifier.weight(1f))

        }
        // TODO Have ViewModel send search bar state back up and update visibility accordingly.
        LazyColumn(
            modifier = Modifier
                .padding(top = 44.dp, start = 56.dp, end = 56.dp)
        ) {
            items(testCards) {
                TradeSearchItem(card = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeScreenPreview() {
    TradeScreen { }
}

