package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R

@Composable
fun PriceLabel(
    price: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                colorResource(id = R.color.mid_purple),
                RoundedCornerShape(percent = 100)
            ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            text = price,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = colorResource(id = R.color.white)
        )
    }
}