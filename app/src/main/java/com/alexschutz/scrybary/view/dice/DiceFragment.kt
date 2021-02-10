package com.alexschutz.scrybary.view.dice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexschutz.scrybary.databinding.FragmentDiceBinding
import com.alexschutz.scrybary.view.BackButtonFragment

class DiceFragment : BackButtonFragment() {

    private var _binding: FragmentDiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDiceBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.listener = this

        return view
    }
}