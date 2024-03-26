package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R

@Composable
fun TraderListItem(
    name: String,
    setSymbol: String,
    modifier: Modifier = Modifier,
    isFoil: Boolean = true,
    price: String,
    isSelected: Boolean,
) {
    // TODO Incorporate price and set into card info.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isSelected) colorResource(id = R.color.dimmed_grey)
                else Color.Transparent
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier
            .weight(1f)
            .padding(end = 20.dp)
        ) {
            MiscCardInfoText(
                text = name,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1F)
            )
            if (isFoil) {
                Image(
                    painter = painterResource(id = R.drawable.ic_foil),
                    contentDescription = "Foil indicator",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(16.dp)
                        .align(Alignment.CenterVertically)

                )
            }
        }
        MiscCardInfoText(
            text = setSymbol,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(0.20f)
        )
        MiscCardInfoText(
            text = price,
            modifier = Modifier.weight(0.30f),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun MiscCardInfoText(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
        fontSize = 16.sp,
        color = colorResource(id = R.color.white),
        textAlign = textAlign,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(showBackground = true)
@Composable
fun TradeListItemPreview() {
    TraderListItem("Tarmogoyf", "MM3", Modifier, true, "$8.50", false)
}