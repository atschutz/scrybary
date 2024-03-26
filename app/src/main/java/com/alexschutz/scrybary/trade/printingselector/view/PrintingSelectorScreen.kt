package com.alexschutz.scrybary.trade.printingselector.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.toDollars
import com.alexschutz.scrybary.trade.printingselector.PrintingSelectorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PrintingSelectorScreen(
    onBackClicked: () -> Unit,
    onAddClicked: (tradeInfo: CardTradeInfo, isP1: Boolean) -> Unit,
) {
    val viewModel: PrintingSelectorViewModel = hiltViewModel()
    val pagerState = rememberPagerState { viewModel.printingData.size }
    val coroutineScope = rememberCoroutineScope()

    if (viewModel.printingData.isNotEmpty()) {
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
                            if (printingData.isNotEmpty()) {
                                coroutineScope.launch {
                                    if (pagerState.canScrollBackward) {
                                        pagerState.animateScrollToPage(
                                           pagerState.currentPage - 1
                                        )
                                    } else pagerState.scrollToPage(pagerState.pageCount - 1)
                                    isFoil =
                                        viewModel.printingData[pagerState.currentPage].startFoil
                                }
                            }
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
                            if (printingData.isNotEmpty()) {
                                coroutineScope.launch {
                                    if (pagerState.canScrollForward) {
                                        pagerState.animateScrollToPage(
                                            pagerState.currentPage + 1
                                        )
                                    } else pagerState.scrollToPage(pagerState.initialPage)
                                    isFoil =
                                        viewModel.printingData[pagerState.currentPage].startFoil
                                }
                            }
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
                    .padding(horizontal = 60.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.name,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.white),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                )
                PriceLabel(
                    price =
                        with(viewModel.printingData[pagerState.currentPage].set) {
                            if (viewModel.isFoil) prices?.usdFoil?.toDollars()
                            else prices?.usd?.toDollars()
                        } ?: "$0.00",
                    modifier = Modifier
                )
                HorizontalPager(
                    state = pagerState,
                    key = { viewModel.printingData[it].index },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1F),
                ) {
                    CardColumn(
                        cardSet = viewModel.printingData[it].set,
                        isFoil = viewModel.isFoil,
                        modifier = Modifier
                    )
                }
                SetDetailsBar(
                    viewModel = viewModel,
                    currentPrinting = viewModel.printingData[pagerState.currentPage],
                    onAddClicked = onAddClicked,
                )
            }
        }
    }
}

