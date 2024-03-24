package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel

@Composable
fun TradeListTopBar(
    viewModel: TradeListViewModel,
    onNavigate: (Int) -> Unit,
    onSearch: (String) -> Unit,
    onClearClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.search_header),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .clickable {
                        onNavigate(R.id.action_tradeFragment_to_menuFragment)
                    }
            )
            SearchField(
                viewModel = viewModel,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                onSearch(viewModel.searchQuery)
            }
            Image(
                painter = painterResource(
                    if (viewModel.selectedCard == null) R.drawable.ic_x
                    else R.drawable.ic_trash
                ),
                contentDescription = "Clear button",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
                    .clickable {
                        if (viewModel.selectedCard == null) {
                            onClearClicked()
                        } else {
                            onDeleteClicked()
                        }
                    }
            )
        }
    }
}
