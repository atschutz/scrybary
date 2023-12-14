package com.alexschutz.scrybary.view.trade.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R

@Composable
fun TradeListMiddleBar() {
    var isListView by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        Box (modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.not_legal_grey))
            .align(Alignment.Center)
        )
        Text(
            text ="$0.00",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .padding(top = 4.dp, end = 8.dp)
                .align(Alignment.TopEnd)
        )
        Text(
            text ="$0.00",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 4.dp, end = 8.dp)
                .align(Alignment.BottomEnd)
        )
        Row(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.mid_purple),
                    RoundedCornerShape(percent = 100)
                )
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxHeight()
                .align(Alignment.Center)

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_list),
                contentDescription = "Show as list",
                colorFilter =
                    if (isListView) null
                    else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isListView = true }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_grid),
                contentDescription = "Show as grid",
                colorFilter =
                    if (isListView) ColorFilter.tint(colorResource(id = R.color.deselected_purple))
                    else null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isListView = false }
            )
            Text(
                text ="$0.00",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .align(Alignment.CenterVertically)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Back button",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeListMiddleBarPreview() {
    TradeListMiddleBar()
}
