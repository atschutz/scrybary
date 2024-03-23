package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardSet

@Composable
fun CardColumn(cardSet: CardSet, isFoil: Boolean, modifier: Modifier = Modifier) {
    val localDensity = LocalDensity.current
    var cardHeight by remember { mutableStateOf(0.dp) }
    var cardWidth by remember { mutableStateOf(0.dp) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(
                        cardSet.imageUri
                    ).build(),
                placeholder = painterResource(id = R.drawable.card_placeholder),
                contentDescription = "Card image",
                modifier = Modifier
                    .aspectRatio(0.71428573f)
                    .align(Alignment.Center)
                    .onGloballyPositioned {
                        with(localDensity) {
                            cardWidth = it.size.width.toDp()
                            cardHeight = it.size.height.toDp()
                        }
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.foil_overlay),
                contentDescription = "Foil overlay",
                alpha = if (isFoil) 1.0f else 0.0f,
                contentScale = ContentScale.Crop,
                modifier = androidx.compose.ui.Modifier
                    .width(cardWidth)
                    .height(cardHeight)
                    .aspectRatio(0.71428573f)
                    .align(Alignment.Center),
            )
        }
        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.deselected_purple),
                    RoundedCornerShape(bottomStart = 40F, bottomEnd = 40F)
                )
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = with(cardSet) { "$set - $symbol" },
                color = colorResource(id = R.color.white)
            )
        }
    }
}