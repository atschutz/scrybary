package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel

@Composable
fun SearchList(
    viewModel: TradeListViewModel,
    onSearchItemClicked: (Card) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (viewModel.isSearchListLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.dimmed_grey))
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.light_purple),
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        } else {
            items(viewModel.searchListCards) {
                TradeSearchItem(card = it) {
                    onSearchItemClicked(it)
                    viewModel.clearSearch()
                }
            }
        }
    }
}