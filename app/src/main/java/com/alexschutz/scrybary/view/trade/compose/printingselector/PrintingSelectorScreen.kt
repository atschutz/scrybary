package com.alexschutz.scrybary.view.trade.compose.printingselector

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.math.MathUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.PriceData

@Composable
fun PrintingSelectorScreen(onBackClicked: () -> Unit) {
    // TODO pass CardDetails then get ImageUris.
    val viewModel: PrintingSelectorViewModel = viewModel()

    var isFoil by remember { mutableStateOf(false) }

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
                        currentPrintingIndex = MathUtils.clamp(
                            currentPrintingIndex - 1, 0, cardSets.size - 1
                        )
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
                        currentPrintingIndex = MathUtils.clamp(
                            currentPrintingIndex + 1, 0, cardSets.size - 1
                        )
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
            // TODO resize so giant names don't ruin everything.
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = viewModel.cardName,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = colorResource(id = R.color.white)
            )
            // TODO handle auto-foiling.
            // TODO Block out possibilities of non-priced prints.
            PriceLabel(
                price =
                    with(getCardSet(viewModel.cardSets, viewModel.currentPrintingIndex)) {
                        if (isFoil) prices?.usdFoil?.toString()
                        else prices?.usd?.toString()
                    } ?: "TODO"
            )
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally),
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(
                            getCardSet(viewModel.cardSets, viewModel.currentPrintingIndex).imageUri
                        ).build(),
                    placeholder = painterResource(id = R.drawable.card_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.71428573f),
                    contentDescription = "Card placeholder",
                    contentScale = ContentScale.Fit,
                )
                // TODO Size is still very slightly off!!!
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.foil_overlay),
                    contentDescription = "Foil overlay",
                    alpha = if (isFoil) 1.0f else 0.0f,
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
                    text = with(getCardSet(viewModel.cardSets, viewModel.currentPrintingIndex)) {
                        "$set - $symbol"
                    },
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
                AddButton(isTop = true)
                Divider()
                Image(
                    painter = painterResource(id = R.drawable.ic_foil),
                    contentDescription = "Foil toggle",
                    colorFilter =
                        if (isFoil) null
                        else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp,)
                        .size(28.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { isFoil = !isFoil }
                )
                Divider()
                AddButton(isTop = false)
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
            fontSize = 32.sp,
            color = colorResource(id = R.color.white)
        )
    }
}

@Composable
fun AddButton(isTop: Boolean = false) {
    // TODO make padding look nicer.
    Box(
        modifier = Modifier
            .padding()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
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

@Preview(showBackground = true)
@Composable
fun ConditionAndSetScreenPreview() {
    PrintingSelectorScreen { }
}

// Get card set from list with given index, or return a placeholder if the list is empty.
fun getCardSet(cardSets: List<CardSet>, index: Int): CardSet =
    if (cardSets.isNotEmpty()) cardSets[index]
    else {
         CardSet(
             "", "", "",
             PriceData(0f, 0f)
         )
    }