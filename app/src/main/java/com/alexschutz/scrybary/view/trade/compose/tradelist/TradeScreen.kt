package com.alexschutz.scrybary.view.trade.compose.tradelist

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexschutz.scrybary.model.Card

@Composable
fun TradeScreen(
    onNavigate: (Int) -> Unit,
    onCardClicked: (Card) -> Unit,
) {
    val viewModel: TradeListViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TradeListTopBar(
                onNavigate,
                { search -> viewModel.fetchFromRemoteWithSearch(search) },
                { viewModel.searchListCards = listOf() }
            )
            TraderColumn(modifier = Modifier.weight(1f), viewModel.p1List)
            TradeListMiddleBar()
            TraderColumn(modifier = Modifier.weight(1f), viewModel.p2List)
        }
        // TODO Have ViewModel send search bar state back up and update visibility accordingly.
        LazyColumn(
            modifier = Modifier
                .padding(top = 44.dp, bottom = 56.dp, start = 56.dp, end = 56.dp)
        ) {
            items(viewModel.searchListCards) { TradeSearchItem(card = it) { onCardClicked(it) } }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeScreenPreview() {
    TradeScreen({ }, { })
}

