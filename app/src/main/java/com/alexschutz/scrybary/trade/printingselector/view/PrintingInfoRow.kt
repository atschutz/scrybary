package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardSet

@Composable
fun PrintingInfoRow(
    cardSet: CardSet,
    cardNumber: Int,
    cardTotal: Int,
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                colorResource(id = R.color.deselected_purple),
                RoundedCornerShape(bottomStart = 40F, bottomEnd = 40F)
            )
            .width(width)
            .padding(8.dp)
    ) {
        Text(
            text = with(cardSet) { "$set - $symbol" },
            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
            fontSize = 12.sp,
            color = colorResource(id = R.color.white),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = "$cardNumber/$cardTotal",
            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
            fontSize = 12.sp,
            color = colorResource(id = R.color.white),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}