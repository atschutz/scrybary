package com.alexschutz.scrybary.view.trade.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.view.trade.compose.detail.CardCondition.NEAR_MINT
import com.alexschutz.scrybary.view.trade.compose.detail.CardCondition.MODERATELY_PLAYED
import com.alexschutz.scrybary.view.trade.compose.detail.CardCondition.HEAVILY_PLAYED
import com.alexschutz.scrybary.view.trade.compose.detail.CardCondition.DAMAGED


@Composable
fun ConditionBottomBar() {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.search_header),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
        ) {
            var selectedCondition by remember { mutableStateOf(NEAR_MINT) }
            var isFoil by remember { mutableStateOf(false) }

            ConditionText(
                condition = NEAR_MINT,
                isSelected = selectedCondition == NEAR_MINT
            ) { selectedCondition = NEAR_MINT }
            ConditionText(
                condition = MODERATELY_PLAYED,
                isSelected = selectedCondition == MODERATELY_PLAYED
            ) { selectedCondition = MODERATELY_PLAYED }
            ConditionText(
                condition = HEAVILY_PLAYED,
                isSelected = selectedCondition == HEAVILY_PLAYED
            ) { selectedCondition = HEAVILY_PLAYED }
            ConditionText(
                condition = DAMAGED,
                isSelected = selectedCondition == DAMAGED
            ) { selectedCondition = DAMAGED }
            Box(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .width(1.dp)
                    .height(32.dp)
                    .background(colorResource(id = R.color.mid_purple))
            )
            Image(
                painter = painterResource(id = R.drawable.ic_foil),
                contentDescription = "Toggle is foil",
                colorFilter =
                    if (isFoil) null
                    else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
                modifier = Modifier
                    .padding(start = 4.dp, top = 6.dp)
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isFoil = !isFoil }
            )
        }
    }
}

@Composable
fun ConditionText(condition: CardCondition, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = condition.text,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
        color = colorResource(id = if (isSelected) R.color.white else R.color.deselected_purple),
        modifier = Modifier
            .padding(end = 6.dp)
            .clickable {
                onClick()
            }
    )
}

@Preview(showBackground = true)
@Composable
fun ConditionBottomBarPreview() {
    ConditionBottomBar()
}

enum class CardCondition(val text: String) {
    NEAR_MINT("NM"),
    MODERATELY_PLAYED("MP"),
    HEAVILY_PLAYED("HP"),
    DAMAGED("DA"),
}