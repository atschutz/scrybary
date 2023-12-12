package com.alexschutz.scrybary.view.trade.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TraderListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Text(
            text = "Card Name",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "SET",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(0.25f)
        )
        Text(
            text = "CD",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(0.25f)
        )
        Text(
            text = "*F*",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(0.25f)
        )
        Text(
            text = "$0.00",
            fontSize = 12.sp,
            modifier = Modifier
                .weight(0.25f)
        )
    }
}