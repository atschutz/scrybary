package com.alexschutz.scrybary.trade.tradelist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.R
import kotlinx.coroutines.delay

const val SEARCH_LENGTH_THRESHOLD = 3

@Composable
fun SearchField(shouldReset: Boolean, modifier: Modifier = Modifier, onInputChanged: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    if (shouldReset) text = ""

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        decorationBox = {
            Box(
                Modifier
                    .background(Color.White, RoundedCornerShape(percent = 100))
                    .fillMaxWidth()
                    .height(32.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 5.dp)
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.enter_card_name_here),
                        color = colorResource(id = R.color.not_legal_grey),
                        fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
                    )
                }
                it()
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        modifier = modifier.fillMaxWidth()
    )

    LaunchedEffect(key1 = text) {
        delay(500L)
        onInputChanged(text)
    }
}