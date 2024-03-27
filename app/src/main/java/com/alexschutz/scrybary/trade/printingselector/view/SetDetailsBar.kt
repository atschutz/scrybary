package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardData
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.trade.printingselector.PrintingSelectorViewModel

@Composable
fun SetDetailsBar(
    viewModel: PrintingSelectorViewModel,
    currentPrinting: CardData,
    onAddClicked: (tradeInfo: CardTradeInfo, isP1: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                colorResource(id = R.color.mid_purple),
                RoundedCornerShape(percent = 100)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddButton(
            isTop = true,
            tradeInfo = CardTradeInfo(
                id = viewModel.cardId,
                name = viewModel.name,
                cardSet = currentPrinting.set,
                isFoil = viewModel.isFoil,
            ),
            onAddClicked = onAddClicked
        )
        Divider()
        Image(
            painter = painterResource(id = R.drawable.ic_foil),
            contentDescription = "Foil toggle",
            colorFilter =
            // TODO - Signify if foil state can't change.
                if (viewModel.isFoil) null
                else ColorFilter.tint(colorResource(id = R.color.deselected_purple)),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(28.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    if (currentPrinting.canChangeFoil)
                        viewModel.isFoil = !viewModel.isFoil
                }
        )
        Divider()
        AddButton(
            isTop = false,
            tradeInfo = CardTradeInfo(
                id = viewModel.cardId,
                name = viewModel.name,
                cardSet = currentPrinting.set,
                isFoil = viewModel.isFoil,
            ),
            onAddClicked = onAddClicked
        )
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