package com.alexschutz.scrybary.trader.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TraderBottomBar() {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(Color.Blue)
    ) {
        BasicTextField(
            value = "Search...",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(4.dp)
                .background(Color.White)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Text(
            text = "$0.00",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}