package com.alexschutz.scrybary.trader.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R

@Composable
fun TradeListTopBar() {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(Color.Blue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Back button",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
        )
        Text(
            text ="$0.00",
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}
