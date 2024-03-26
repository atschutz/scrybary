package com.alexschutz.scrybary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // TODO view bind on click listeners.

        // Inflate and bind
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        // Set button functionality
        binding.btnLibrary.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_libraryFragment)
        }
        binding.btnTrade.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_tradeFragment)
        }
        binding.btnCounter.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_counterFragment)
        }
        binding.btnDice.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_diceFragment)
        }
        binding.btnSettings.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_settingsFragment)
        }

        return binding.root
    }
}