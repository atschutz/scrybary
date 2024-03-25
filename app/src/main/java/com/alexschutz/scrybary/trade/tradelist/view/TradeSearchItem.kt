package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.symbols
import com.alexschutz.scrybary.trade.testCard

@Composable
fun TradeSearchItem(card: Card, onSearchItemClicked: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.dimmed_grey))
            .padding(4.dp)
            .clickable { onSearchItemClicked() },
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = card.name,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            generateSymbolIdListFromCmc(card.cmc).map {
                it?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = "CMC symbol",
                        modifier = Modifier.size(16.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}

fun generateSymbolIdListFromCmc(cmc: String?): List<Int?> =
    cmc?.split(regex = "(?<=\\})".toRegex())?.map { symbols[it] } ?: listOf()

@Preview(showBackground = true)
@Composable
fun TradeScreenSearchItemPreview() {
    TradeSearchItem(testCard) { }
}
