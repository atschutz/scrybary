package com.alexschutz.scrybary.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentTradeBinding
import com.alexschutz.scrybary.hideKeyboard
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.trade.printingselector.view.PrintingSelectorScreen
import com.alexschutz.scrybary.trade.tradelist.TradeListViewModel
import com.alexschutz.scrybary.trade.tradelist.view.TradeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeFragment : BackButtonFragment() {
    private lateinit var binding: FragmentTradeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTradeBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            // Dispose when view's owner is destroyed.
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val navController = rememberNavController()
                val viewModel: TradeListViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = TradeNav.Trade.name,
                ) {
                    composable(route = TradeNav.Trade.name) {
                        TradeScreen(
                            viewModel = viewModel,
                            onNavigate = { id -> findNavController().navigate(id) },
                            onSearchItemClicked = {
                                hideKeyboard()
                                navController.navigate(
                                    route =
                                        "${TradeNav.PrintingSelector.name}/${it.id}/${it.name}"
                                )
                            },
                            hideKeyboard = { hideKeyboard() }
                        )
                    }
                    composable(
                        route = "${TradeNav.PrintingSelector.name}/{card_id}/{card_name}",
                        arguments = listOf(
                            navArgument("card_id") { type = NavType.StringType },
                            navArgument("card_name") { type = NavType.StringType },
                        )
                    ) {
                        PrintingSelectorScreen(
                            onBackClicked = {
                                navController.navigate(route = TradeNav.Trade.name)
                            },
                            onAddClicked = { cardTradeInfo, isP1 ->
                                viewModel.addCard(cardTradeInfo, isP1)
                                navController.navigate(route = TradeNav.Trade.name)
                            }
                        )
                    }
                }
            }
        }

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_tradeFragment_to_menuFragment)
    }

    enum class TradeNav {
        Trade,
        PrintingSelector,
    }
}