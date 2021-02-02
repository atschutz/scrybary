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

    // View binding boiler plate
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Inflate and bind
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set button functionality
        binding.btnLibrary.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_libraryFragment)
        }
        binding.btnCounter.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_counterFragment)
        }
        binding.btnDice.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_diceFragment)
        }

        return view
    }
}