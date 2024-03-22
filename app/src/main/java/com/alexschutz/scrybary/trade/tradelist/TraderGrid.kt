package com.alexschutz.scrybary.trade.tradelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardTradeInfo

const val ROW_SIZE = 3

@Composable
fun TraderGrid(
    modifier: Modifier,
    list: List<CardTradeInfo>,
) {
    val localDensity = LocalDensity.current
    var cardHeight by remember { mutableStateOf(0.dp) }
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp)
    ) {
        val chunks = list.chunked(ROW_SIZE)
        items(chunks) { chunk ->
            Row {
                chunk.forEach {
                    Box(
                        modifier = Modifier.weight(1F)
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
                    }
                }
                if (chunk.size < ROW_SIZE) {
                    Box(modifier = Modifier.weight(ROW_SIZE - chunk.size.toFloat()))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TraderGridPreview() {
    TraderGrid(Modifier, listOf())
}