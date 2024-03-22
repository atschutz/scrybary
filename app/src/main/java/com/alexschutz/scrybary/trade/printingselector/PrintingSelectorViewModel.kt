package com.alexschutz.scrybary.trade.printingselector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexschutz.scrybary.model.CardData
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardsApiService
import com.alexschutz.scrybary.trade.model.TradeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PrintingSelectorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TradeRepository,
): ViewModel() {
    val name: String = checkNotNull(savedStateHandle["card_name"])
    val cardId: String = checkNotNull(savedStateHandle["card_id"])

    var printingData: List<CardData> by mutableStateOf(listOf())

    // Currently focused card. Blank card if empty data.
    var currentPrinting: CardData by mutableStateOf(BLANK_CARD)

    var isFoil: Boolean by mutableStateOf(false)

    init {
        fetchCardPrintings(cardId)
    }

    private fun fetchCardPrintings(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            printingData = repository.fetchCardSets(id)
            currentPrinting =
                if (printingData.isEmpty()) BLANK_CARD
                else printingData.first()
            isFoil = currentPrinting.startFoil
        }
    }

    fun clearPrintingData() {
        printingData = listOf()
        currentPrinting = BLANK_CARD
        isFoil = false
    }

    companion object {
        val BLANK_CARD =
            CardData(
                CardSet(
                    set = null,
                    symbol = null,
                    imageUri = null,
                ),
                index = -1
            )
    }
}