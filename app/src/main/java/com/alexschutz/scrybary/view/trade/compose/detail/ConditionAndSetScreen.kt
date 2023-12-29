package com.alexschutz.scrybary.view.trade.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexschutz.scrybary.R

@Composable
fun ConditionAndSetScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .clickable {
                    // TODO Go back.
                },
        ) {
            Image(
                modifier = Modifier
                    .padding(6.dp)
                    .size(36.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back button",
                contentScale = ContentScale.Fit,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 60.dp, end = 60.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Tarmogoyf",
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = colorResource(id = R.color.white)
            )
            PriceLabel(price = "$37.50")
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),

                painter = painterResource(id = R.drawable.card_placeholder),
                contentDescription = "Card placeholder",
                contentScale = ContentScale.Fit,
            )
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
                    text = "Modern Masters 3 - MM3",
                    color = colorResource(id = R.color.white)
                )
            }
            Row {
                AddButton(isTop = true)
                AddButton(isTop = false)
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            ConditionBottomBar()
        }

    }
}

@Composable
fun PriceLabel(price: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .background(
                colorResource(id = R.color.mid_purple),
                RoundedCornerShape(percent = 100)
            ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            text = price,
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
            .padding(top = 32.dp, start = 30.dp, end = 30.dp)
            .background(
                colorResource(id = R.color.mid_purple),
                RoundedCornerShape(percent = 100)
            )
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

@Preview(showBackground = true)
@Composable
fun ConditionAndSetScreenPreview() {
    ConditionAndSetScreen()
}