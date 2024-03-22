package com.alexschutz.scrybary.trade.tradelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexschutz.scrybary.R

@Composable
fun TradeListTopBar(
    onNavigate: (Int) -> Unit,
    onSearchPressed: (String) -> Unit,
    onClearClicked: () -> Unit,
) {
    var search by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchable by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.search_header),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .clickable {
                        onNavigate(R.id.action_tradeFragment_to_menuFragment)
                    }
            )
            // TODO Set fonts.
            BasicTextField(
                value = search,
                onValueChange = {
                    search = it
                    if (!isSearchable) isSearchable = true
                },
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
                        if (search.text.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.enter_card_name_here),
                                color = colorResource(id = R.color.not_legal_grey),
                                fontFamily = FontFamily(Font(R.font.montserrat_ttf)),
                            )
                        }
                        it()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
                    .weight(1f)
            )
            Image(
                painter = painterResource(
                    id = if (isSearchable) R.drawable.ic_search else R.drawable.ic_x
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
                    .clickable {
                        if (isSearchable && search.text.length >= 3) {
                            onSearchPressed(search.text)
                            isSearchable = !isSearchable
                        } else if (!isSearchable) {
                            // TODO clear list.
                            search = TextFieldValue("")
                            isSearchable = true
                            onClearClicked()
                        }
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeListTopBarPreview() {
    TradeListTopBar({ }, { }, { })
}