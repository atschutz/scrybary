package com.alexschutz.scrybary.view.dice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import com.alexschutz.scrybary.RollTotal
import com.alexschutz.scrybary.databinding.FragmentDiceBinding
import com.alexschutz.scrybary.roll
import com.alexschutz.scrybary.view.BackButtonFragment

class DiceFragment : BackButtonFragment(), RollClickListener {

    private var _binding: FragmentDiceBinding? = null
    private val binding get() = _binding!!

    private var numberOfPlayers = 0
    private var numberOfDice = 0
    private var diceSides = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDiceBinding.inflate(inflater, container, false)

        binding.backListener = this
        binding.rollListener = this

        binding.p1Roll = RollTotal()
        binding.p2Roll = RollTotal()

        // Set numberOfPlayers to index of checked button + 1.
        binding.rgPlayers.setOnCheckedChangeListener { group, checkedId ->
            numberOfPlayers = group.indexOfChild(view?.findViewById<RadioButton>(checkedId)) + 1
        }
        // Set numberOfDice to index of checked button + 1.
        binding.rgCount.setOnCheckedChangeListener { group, checkedId ->
            numberOfDice = group.indexOfChild(view?.findViewById<RadioButton>(checkedId)) + 1
        }
        // The first 5 types of die have an amount of sides that increment up by 2, starting at 4.
        // So we add 2 to the index then multiply by 2. The 6th type arbitrarily has 20 sides.
        // d4 -> 0th index = 0 + 2 * 2 = 4
        // d6 -> 1st index = 1 + 2 * 2 = 6
        // d8 -> 2nd index = 2 + 2 * 2 = 8 etc.
        binding.rgSides.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(view?.findViewById<RadioButton>(checkedId))
            diceSides = if (index < 5) { (index + 2) * 2 } else { 20 }
        }

        return binding.root
    }   

    override fun onRollClicked(v: View) {
        binding.p1Roll = roll(numberOfDice, diceSides)
        if (numberOfPlayers > 1) binding.p2Roll = roll(numberOfDice, diceSides)
    }
}