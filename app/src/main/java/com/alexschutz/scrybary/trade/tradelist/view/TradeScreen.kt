package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel

@Composable
fun TradeScreen(
    viewModel: TradeListViewModel,
    onNavigate: (Int) -> Unit,
    onSearchItemClicked: (Card) -> Unit,
    hideKeyboard: () -> Unit,
) {
    val localDensity = LocalDensity.current
    var middleBarHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TradeListTopBar(
                viewModel = viewModel,
                onNavigate = onNavigate,
                onSearch = { search ->
                    viewModel.searchQuery = search
                    if (search.length < SEARCH_LENGTH_THRESHOLD) {
                        viewModel.searchListCards = listOf()
                    } else viewModel.fetchFromRemoteWithSearch(search)
                },
                onClearClicked = {
                    hideKeyboard()
                    viewModel.clearSearch()
                },
                onDeleteClicked = {
                    hideKeyboard()
                    viewModel.clearSearch()
                    viewModel.deleteCard(viewModel.selectedCard, viewModel.isP1Selected)
                    viewModel.selectedCard = null
                }
            )
            Box {
                Column {
                    TraderView(
                        modifier = Modifier.weight(1f),
                        list = viewModel.p1List,
                        isListMode = viewModel.isListView,
                        margin = middleBarHeight / 2,
                        isTop = true,
                        onListItemClicked = {
                            hideKeyboard()
                            viewModel.isP1Selected = true
                            viewModel.selectedCard = if (viewModel.selectedCard == it) null else it
                        },
                    )
                    TraderView(
                        modifier = Modifier.weight(1f),
                        list = viewModel.p2List,
                        isListMode = viewModel.isListView,
                        margin = middleBarHeight / 2,
                        isTop = false,
                        onListItemClicked = {
                            hideKeyboard()
                            viewModel.isP1Selected = false
                            viewModel.selectedCard = if (viewModel.selectedCard == it) null else it
                        },
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
        SearchList(
            viewModel = viewModel,
            onSearchItemClicked = onSearchItemClicked,
            modifier = Modifier
                .padding(top = 44.dp, bottom = 56.dp, start = 56.dp, end = 56.dp)
        )
    }
}

