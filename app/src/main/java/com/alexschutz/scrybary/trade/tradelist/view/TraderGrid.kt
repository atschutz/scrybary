package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.toDollars

const val ROW_SIZE = 3

@Composable
fun TraderGrid(
    modifier: Modifier,
    list: List<CardTradeInfo>,
    margin: Dp,
    isTop: Boolean,
    onListItemClicked: (card: CardTradeInfo) -> Unit,
) {
    val localDensity = LocalDensity.current
    var cardHeight by remember { mutableStateOf(0.dp) }
    var selectedCard by remember { mutableStateOf<CardTradeInfo?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxHeight()
    ) {
        val chunks = list.chunked(ROW_SIZE)

        if (!isTop) item { Box(modifier = Modifier.height(margin)) }
        items(chunks) { chunk ->
            Row {
                chunk.forEach {
                    val isSelected = selectedCard == it
                    val price =
                        if (it.isFoil) it.cardSet.prices?.usdFoil?.toDollars() ?: ""
                        else it.cardSet.prices?.usd?.toDollars() ?: ""

                    Box(
                        modifier = Modifier
                            .border(
                                width = if (isSelected) 4.dp else 0.dp,
                                color =
                                if (isSelected) colorResource(id = R.color.light_purple)
                                else Color.Transparent
                            )
                            .weight(1F)
                            .clickable {
                                selectedCard = if (selectedCard == it) null else it
                                onListItemClicked(it)
                            }
                    ) {
                        AsyncImage(
                            model = it.cardSet.imageUri,
                            contentDescription = "Image of ${it.name}",
                            placeholder = painterResource(id = R.drawable.card_placeholder),
                            modifier = Modifier.onGloballyPositioned {
                                with(localDensity) { cardHeight = it.size.height.toDp() }
                            }
                        )
                        Image(
                            modifier = Modifier.height(cardHeight),
                            painter = painterResource(id = R.drawable.foil_overlay),
                            contentDescription = "Foil overlay",
                            alpha = if (it.isFoil) 1.0f else 0.0f,
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = price,
                            fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.white),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(bottom = 60.dp)
                                .background(
                                    color = colorResource(id = R.color.primary_grey_transparent),
                                    shape = RoundedCornerShape(100)
                                )
                                .padding(horizontal = 8.dp)
                                .align(Alignment.Center)
                                .alpha(0.5F)
                        )
                    }
                }
                if (chunk.size < ROW_SIZE) {
                    Box(modifier = Modifier.weight(ROW_SIZE - chunk.size.toFloat()))
                }
            }

        }
        if (isTop) item { Box(modifier = Modifier.height(margin)) }
    }
}

@Preview(showBackground = true)
@Composable
fun TraderGridPreview() {
    TraderGrid(Modifier, listOf(), 0.dp, true) { }
}