package com.alexschutz.scrybary.view.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.databinding.FragmentTradeBinding
import com.alexschutz.scrybary.view.trade.compose.TradeScreen

class TradeFragment : Fragment() {
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
            setContent { TradeScreen() }
        }

        return binding.root
    }
}