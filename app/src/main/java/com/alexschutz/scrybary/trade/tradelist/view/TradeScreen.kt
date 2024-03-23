package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel

@Composable
fun TradeScreen(
    viewModel: TradeListViewModel,
    onNavigate: (Int) -> Unit,
    onCardClicked: (Card) -> Unit,
    onListItemClicked: (card: CardTradeInfo) -> Unit,
) {
    val localDensity = LocalDensity.current
    var middleBarHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TradeListTopBar(
                onNavigate,
                { search ->
                    if (search.length < SEARCH_LENGTH_THRESHOLD) {
                        viewModel.searchListCards = listOf()
                    } else viewModel.fetchFromRemoteWithSearch(search)
                },
                { viewModel.searchListCards = listOf() }
            )
            Box {
                Column {
                    TraderView(
                        modifier = Modifier.weight(1f),
                        list = viewModel.p1List,
                        isListMode = viewModel.isListView,
                        margin = middleBarHeight / 2,
                        isTop = true,
                        onListItemClicked = onListItemClicked,
                    )
                    TraderView(
                        modifier = Modifier.weight(1f),
                        list = viewModel.p2List,
                        isListMode = viewModel.isListView,
                        margin = middleBarHeight / 2,
                        isTop = false,
                        onListItemClicked = onListItemClicked,
                    )
                }
                TradeListMiddleBar(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .onGloballyPositioned {
                            with(localDensity) {
                                middleBarHeight = it.size.height.toDp()
                            }
                        }
                )
            }
        }
        // TODO Have ViewModel send search bar state back up and update visibility accordingly.
        LazyColumn(
            modifier = Modifier
                .padding(top = 44.dp, bottom = 56.dp, start = 56.dp, end = 56.dp)
        ) {
            items(viewModel.searchListCards) {
                TradeSearchItem(card = it) {
                    onCardClicked(it)
                    viewModel.searchListCards = listOf()
                }
            }
        }
    }
}

