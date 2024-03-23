package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardTradeInfo

@Composable
fun AddButton(
    isTop: Boolean = false,
    tradeInfo: CardTradeInfo,
    onAddClicked: (tradeInfo: CardTradeInfo, isP1: Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onAddClicked(tradeInfo, isTop) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 6.dp)
                    .size(24.dp)
                    .rotate(if (isTop) 180f else (0f)),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Back button",
                contentScale = ContentScale.FillWidth,
            )
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = "+",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}