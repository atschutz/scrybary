package com.alexschutz.scrybary.view.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentTradeBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.view.trade.compose.printingselector.PrintingSelectorScreen
import com.alexschutz.scrybary.view.trade.compose.tradelist.TradeScreen

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

                NavHost(
                    navController = navController,
                    startDestination = TradeScreen.Trade.name,
                ) {
                    composable(route = TradeScreen.Trade.name) {
                        TradeScreen(
                            onNavigate = { id -> findNavController().navigate(id) },
                            onCardClicked = {
                                navController.navigate(
                                    route =
                                        "${TradeScreen.PrintingSelector.name}/${it.id}/${it.name}"
                                )
                            },
                        )
                    }
                    composable(
                        route = "${TradeScreen.PrintingSelector.name}/{card_id}/{card_name}",
                        arguments = listOf(
                            navArgument("card_id") { type = NavType.StringType },
                            navArgument("card_name") { type = NavType.StringType },
                        )
                    ) {
                        PrintingSelectorScreen(
                            onBackClicked = {
                                navController.navigate(route = TradeScreen.Trade.name)
                            },
                            onAddClicked = { cardTradeInfo, isP1 ->
                                navController.navigate(route = TradeScreen.Trade.name)
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

    enum class TradeScreen {
        Trade,
        PrintingSelector,
    }
}