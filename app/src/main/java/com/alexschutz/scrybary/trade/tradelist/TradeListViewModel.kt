package com.alexschutz.scrybary.trade.tradelist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.trade.model.TradeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeListViewModel @Inject constructor(
    private val repository: TradeRepository,
) : ViewModel() {
    var searchQuery by mutableStateOf("")

    var isListView by mutableStateOf(true)

    var searchListCards: List<Card> by mutableStateOf(listOf())

    var p1List: List<CardTradeInfo> by mutableStateOf(listOf())
    var p2List: List<CardTradeInfo> by mutableStateOf(listOf())

    val p1Total by derivedStateOf { getPlayerTotal(p1List) }
    val p2Total by derivedStateOf { getPlayerTotal(p2List) }

    val difference by derivedStateOf { p1Total - p2Total }

    var selectedCard by mutableStateOf<CardTradeInfo?>(null)
    var isP1Selected by mutableStateOf(true)

    var isSearchListLoading by mutableStateOf(false)

    fun fetchFromRemoteWithSearch(search: String) {
        isSearchListLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            searchListCards = repository.fetchFromRemoteWithSearch(search).filterNot {
                it.name.startsWith(ALCHEMY_SIGNIFIER)
            }
            isSearchListLoading = false
        }
    }

    fun addCard(cardTradeInfo: CardTradeInfo, isP1: Boolean) {
        if (isP1) p1List = p1List + cardTradeInfo
        else p2List = p2List + cardTradeInfo
    }

    fun deleteCard(cardTradeInfo: CardTradeInfo?, isP1: Boolean) {
        cardTradeInfo?.let {
            if (isP1) p1List = p1List - cardTradeInfo
            else p2List = p2List - cardTradeInfo
        }
    }

    fun clearSearch() {
        searchQuery = ""
        searchListCards = listOf()
    }

    private fun getPlayerTotal(cards: List<CardTradeInfo>): Float =
        cards.map { it.price }.reduceOrNull {acc, price -> acc + price } ?: 0.00F

    companion object {
        // Alchemy set cards are only on MTG Arena and thus not priced.
        const val ALCHEMY_SIGNIFIER = "A-"
    }
}