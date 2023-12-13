package com.alexschutz.scrybary.view.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentTradeBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.view.trade.compose.TradeScreen

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
            setContent { TradeScreen { id -> findNavController().navigate(id) } }
        }

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_tradeFragment_to_menuFragment)
    }
}