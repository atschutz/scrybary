package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    id: String,
    name: String,
    setSymbol: String,
    isFoil: Boolean = true,
    price: String
) {
    // TODO Incorporate price and set into card info.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier
            .weight(1f)
            .padding(end = 20.dp)
        ) {
            MiscCardInfoText(text = name, modifier = Modifier.weight(1F))
            if (isFoil) {
                Image(
                    painter = painterResource(id = R.drawable.ic_foil),
                    contentDescription = "CMC symbol",
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp)
                        .align(Alignment.CenterVertically)

                )
            }
        }
        MiscCardInfoText(text = setSymbol, modifier = Modifier.weight(0.20f))

        MiscCardInfoText(text = price, modifier = Modifier.weight(0.30f), true)
    }
}

@Composable
fun MiscCardInfoText(text: String, modifier: Modifier = Modifier, alignToEnd: Boolean = false) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
        fontSize = 16.sp,
        color = colorResource(id = R.color.white),
        textAlign = if (alignToEnd) TextAlign.End else TextAlign.Start,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(showBackground = true)
@Composable
fun TradeListItemPreview() {
    TraderListItem("1234", "Tarmogoyf", "MM3", true, "$8.50")
}