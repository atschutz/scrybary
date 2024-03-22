package com.alexschutz.scrybary.trade.printingselector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardTradeInfo

@Composable
fun PrintingSelectorScreen(
    onBackClicked: () -> Unit,
    onAddClicked: (tradeInfo: CardTradeInfo, isP1: Boolean) -> Unit,
) {
    val viewModel: PrintingSelectorViewModel = hiltViewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .padding(6.dp)
                .size(36.dp)
                .clickable { onBackClicked() },
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back button",
            contentScale = ContentScale.Fit,
        )
        Image(
            modifier = Modifier
                .padding(6.dp)
                .size(36.dp)
                .align(Alignment.CenterStart)
                .rotate(90f)
                .clickable {
                    with(viewModel) {
                        currentPrinting = printingData[
                            if (currentPrinting.index - 1 < 0) printingData.size - 1
                            else currentPrinting.index - 1
                        ]
                        isFoil = currentPrinting.startFoil
                    }
                },
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Swipe card printing back",
            contentScale = ContentScale.Fit,
        )
        Image(
            modifier = Modifier
                .padding(6.dp)
                .size(36.dp)
                .align(Alignment.CenterEnd)
                .rotate(-90f)
                .clickable {
                    with(viewModel) {
                        currentPrinting = printingData[
                            if (currentPrinting.index + 1 >= printingData.size) 0
                            else currentPrinting.index + 1
                        ]
                        isFoil = currentPrinting.startFoil
                    }
                },
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Swipe card printing forward",
            contentScale = ContentScale.Fit,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
                .padding(start = 60.dp, end = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = viewModel.name,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = colorResource(id = R.color.white)
            )
            PriceLabel(
                price =
                    with(viewModel.currentPrinting.set) {
                        // TODO 2 decimals always.
                        if (viewModel.isFoil) prices?.usdFoil?.toString()
                        else prices?.usd?.toString()
                    } ?: "0.00"
            )
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally),
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(
                           viewModel.currentPrinting.set.imageUri
                        ).build(),
                    placeholder = painterResource(id = R.drawable.card_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.71428573f),
                    contentDescription = "Card image",
                    contentScale = ContentScale.Fit,
                )
                // TODO Size is still very slightly off!!!
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.foil_overlay),
                    contentDescription = "Foil overlay",
                    alpha = if (viewModel.isFoil) 1.0f else 0.0f,
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.deselected_purple),
                        RoundedCornerShape(percent = 40)
                    )
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = with(viewModel.currentPrinting.set) { "$set - $symbol" },
                    color = colorResource(id = R.color.white)
                )
            }
            Row( modifier = Modifier
                .padding(16.dp)
                .background(
                    colorResource(id = R.color.mid_purple),
                    RoundedCornerShape(percent = 100)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO Dry.
                AddButton(
                    isTop = true,
                    tradeInfo = CardTradeInfo(
                        id = viewModel.cardId,
                        name = viewModel.name,
                        cardSet = viewModel.currentPrinting.set,
                        isFoil = viewModel.isFoil,
                    ),
                    onAddClicked = onAddClicked
                )
                Divider()
                Image(
                    painter = painterResource(id = R.drawable.ic_foil),
                    contentDescription = "Foil toggle",
                    colorFilter =
                        if (viewModel.isFoil) null
                        else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp,)
                        .size(28.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            if (viewModel.currentPrinting.canChangeFoil)
                                viewModel.isFoil = !viewModel.isFoil
                        }
                )
                Divider()
                AddButton(
                    isTop = false,
                    tradeInfo = CardTradeInfo(
                        id = viewModel.cardId,
                        name = viewModel.name,
                        cardSet = viewModel.currentPrinting.set,
                        isFoil = viewModel.isFoil,
                    ),
                    onAddClicked = onAddClicked
                )
            }
        }
    }
}

@Composable
fun PriceLabel(price: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .background(
                colorResource(id = R.color.mid_purple),
                RoundedCornerShape(percent = 100)
            ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            text = "$$price",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = colorResource(id = R.color.white)
        )
    }
}

@Composable
fun AddButton(
    isTop: Boolean = false,
    tradeInfo: CardTradeInfo,
    onAddClicked: (tradeInfo: CardTradeInfo, isP1: Boolean) -> Unit,
) {
    // TODO make padding look nicer.
    Box(
        modifier = Modifier
            .padding()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onAddClicked(tradeInfo, isTop) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 6.dp)
                    .size(24.dp)
                    .rotate(if (isTop) 180f else (0f)),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Back button",
                contentScale = ContentScale.FillWidth,
            )
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = "+",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .width(1.dp)
            .height(32.dp)
            .background(colorResource(id = R.color.light_purple))
    )
}
