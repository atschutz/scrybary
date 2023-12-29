package com.alexschutz.scrybary.view.trade.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .padding(8.dp)
                .clickable {
                    // TODO Go back.
                },
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back button",
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.card_placeholder),
                contentDescription = "Card placeholder",
                contentScale = ContentScale.Crop,
            )
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(40.dp)
                    .background(
                        colorResource(id = R.color.mid_purple),
                        RoundedCornerShape(percent = 100)
                    ),
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    text = "$37.50",
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.white)
                )
            }
            ConditionBottomBar()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ConditionAndSetScreenPreview() {
    ConditionAndSetScreen()
}