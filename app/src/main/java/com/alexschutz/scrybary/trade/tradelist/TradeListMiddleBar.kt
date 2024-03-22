package com.alexschutz.scrybary.trade.tradelist

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.toDollars
import kotlin.math.abs

@Composable
fun TradeListMiddleBar(viewModel: TradeListViewModel) {
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
            text = viewModel.p1Total.toDollars(),
            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.TopEnd)
        )
        Text(
            text = viewModel.p2Total.toDollars(),
            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
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
                    if (viewModel.isListView) null
                    else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { viewModel.isListView = true }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_grid),
                contentDescription = "Show as grid",
                colorFilter =
                    if (viewModel.isListView) ColorFilter.tint(colorResource(id = R.color.deselected_purple))
                    else null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { viewModel.isListView = false }
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(colorResource(id = R.color.light_purple))
            )
            Text(
                text = abs(viewModel.difference).toDollars(),
                fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .align(Alignment.CenterVertically)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Difference signifier",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .rotate(if (viewModel.difference > 0) 180F else 0F)
            )
        }
    }
}
