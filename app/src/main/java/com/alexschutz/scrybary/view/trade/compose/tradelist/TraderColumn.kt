package com.alexschutz.scrybary.view.trade.compose.tradelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.view.trade.compose.printingselector.CardCondition
import com.alexschutz.scrybary.view.trade.compose.testCard
import com.alexschutz.scrybary.view.trade.compose.testCards

@Composable
fun TraderColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        // TODO Make lazy column.
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        ) {
            items(testCards) {
                TraderListItem(
                    testCard,
                    "MM3",
                    CardCondition.NEAR_MINT,
                    true,
                    "$8.50")
            }
        }
    }
}

