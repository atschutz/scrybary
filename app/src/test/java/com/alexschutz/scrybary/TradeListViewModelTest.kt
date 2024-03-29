package com.alexschutz.scrybary

import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.model.CardSet
import com.alexschutz.scrybary.model.CardTradeInfo
import com.alexschutz.scrybary.model.PriceData
import com.alexschutz.scrybary.trade.model.TradeRepository
import com.alexschutz.scrybary.trade.model.TradeWebService
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel
import org.junit.Before
import org.junit.Test

class TradeListViewModelTest {
    private lateinit var viewModel: TradeListViewModel

    private val testPriceData1 = PriceData(
        usd = 2.80F,
        usdFoil = 5.00F,
    )

    private val testSet1 = CardSet(
        set = "I Don't Know",
        symbol = "IDK",
        imageUri = "https://i.dont.know.gov",
        prices = testPriceData1,
    )
    private val testTradeInfo1 = CardTradeInfo(
        id = "1234",
        name = "Scary Monster Guy",
        cardSet = testSet1,
        isFoil = false,
    )

    private val testPriceData2 = PriceData(
        usd = 5.45F,
        usdFoil = 50.00F,
    )

    private val testSet2 = CardSet(
        set = "I Don't Know",
        symbol = "IDK",
        imageUri = "https://i.dont.know.gov",
        prices = testPriceData2,
    )
    private val testTradeInfo2 = CardTradeInfo(
        id = "1234",
        name = "Scary Monster Guy",
        cardSet = testSet2,
        isFoil = false,
    )

    @Before
    fun setup() {
        val service = TradeWebService()
        val repository = TradeRepository(service)

        viewModel = TradeListViewModel(repository)
    }

    @Test
    fun `verify add card to p1`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)

            assert(p1List.size == 1)
            assert(p1List.contains(testTradeInfo1))
        }
    }

    @Test
    fun `verify add card to p2`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)

            assert(p2List.size == 1)
            assert(p2List.contains(testTradeInfo2))
        }
    }

    @Test
    fun `verify delete card from p1`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = true)

            deleteCard(cardTradeInfo = testTradeInfo2, isP1 = true)

            assert(!p1List.contains(testTradeInfo2))
            assert(p1List.size == 4)
        }
    }

    @Test
    fun `verify delete card from p2`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = false)

            deleteCard(cardTradeInfo = testTradeInfo1, isP1 = false)

            assert(!p2List.contains(testTradeInfo1))
            assert(p2List.size == 4)
        }
    }

    @Test
    fun `verify p1 totals are correct`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)

            assert(p1Total == testTradeInfo1.price * 5)
        }
    }

    @Test
    fun `verify p2 totals are correct`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)

            assert(p2Total == testTradeInfo2.price * 5)
        }
    }

    @Test
    fun `verify list total difference`() {
        with(viewModel) {
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)
            addCard(cardTradeInfo = testTradeInfo1, isP1 = true)

            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)
            addCard(cardTradeInfo = testTradeInfo2, isP1 = false)

            assert(difference == p1Total - p2Total)
        }
    }

    @Test
    fun `verify clear search`() {
        val testCard = Card(
            id = "1234",
            name = "Guy",
            cmc = "3UU",
            type = "Creature",
            power = "7",
            toughness = "3",
            loyalty = null,
        )
        with(viewModel) {
            searchQuery = "This is a search query."
            searchListCards = listOf(
                testCard,
                testCard,
                testCard,
                testCard,
            )

            clearSearch()

            assert(searchQuery == "")
            assert(searchListCards == listOf<Card>())
        }
    }
}