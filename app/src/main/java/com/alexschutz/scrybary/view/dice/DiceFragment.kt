package com.alexschutz.scrybary.view.dice

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.RollTotal
import com.alexschutz.scrybary.databinding.FragmentDiceBinding
import com.alexschutz.scrybary.roll
import com.alexschutz.scrybary.view.BackButtonFragment

class DiceFragment : BackButtonFragment(), RollClickListener {

    private lateinit var binding: FragmentDiceBinding

    private var numberOfPlayers = 2
    private var numberOfDice = 2
    private var diceSides = 6

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDiceBinding.inflate(inflater, container, false)

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
        // d4 -> 0th index = (0 + 2) * 2 = 4 etc.
        binding.rgSides.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(view?.findViewById<RadioButton>(checkedId))
            diceSides = if (index < 5) {
                (index + 2) * 2
            } else {
                20
            }
        }

        return binding.root
    }

    override fun onRollClicked(v: View) {

        with(binding) {
            // Player 1 always rolls. Do roll and make visible.
            p1Roll = roll(numberOfDice, diceSides)
            p1TotalContainer.visibility = View.VISIBLE

            drawDice(p1Roll?.rolls ?: arrayListOf(), p1DiceContainer, 1)

            // If we have more than one player, make player 2 total visible and roll.
            // Else, hide player 2 total and set values to 0
            p2Roll =
                if (numberOfPlayers > 1) {

                    p2TotalContainer.visibility = View.VISIBLE
                    p2DiceContainer.visibility = View.VISIBLE

                    val rollTotal = roll(numberOfDice, diceSides)
                    drawDice(rollTotal.rolls, p2DiceContainer, 2)

                    rollTotal
                } else {

                    p2TotalContainer.visibility = View.GONE
                    p2DiceContainer.visibility = View.GONE

                    RollTotal()
                }

            // White the winning player's total, grey the loser's. If it's a tie, white both.
            when {
                p1Roll?.sum ?: 0 > p2Roll?.sum ?: 0 -> {
                    tvP1Total.setTextColor(ContextCompat.getColor(
                            tvP1Total.context,
                            R.color.white
                        )
                    )
                    tvP2Total.setTextColor(ContextCompat.getColor(
                            tvP2Total.context,
                            R.color.dimmed_grey
                        )
                    )
                }
                p1Roll?.sum ?: 0 < p2Roll?.sum ?: 0 -> {
                    tvP1Total.setTextColor(ContextCompat.getColor(
                            tvP1Total.context,
                            R.color.dimmed_grey
                        )
                    )
                    tvP2Total.setTextColor(ContextCompat.getColor(
                            tvP2Total.context,
                            R.color.white
                        )
                    )
                }
                else -> {
                    tvP1Total.setTextColor(ContextCompat.getColor(
                            tvP1Total.context,
                            R.color.white
                        )
                    )
                    tvP2Total.setTextColor(ContextCompat.getColor(
                            tvP2Total.context,
                            R.color.white
                        )
                    )
                }
            }
        }
    }


    // TODO add colors and dynamic sizing.
    private fun drawDice(rolls: ArrayList<Int>, container: LinearLayout, player: Int) {

        container.removeAllViews()

        for (roll in rolls) {

            // Inflate dice view and add to container layout.
            val diceView = View.inflate(context, R.layout.layout_die, null)
            container.addView(diceView, container.childCount - 1)

            // Get dice background and set color based on player.
            diceView.findViewById<ImageView>(R.id.iv_dice_bg)?.let {
                it.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            it.context,
                            if (player == 1) R.color.dice_dark else R.color.dice_light
                        )
                    )
            }
            // Get dice text and set color based on player.
            diceView.findViewById<TextView>(R.id.tv_dice_total)?.let {
                it.setTextColor(
                    ContextCompat.getColor(
                        it.context,
                        if (player == 1) R.color.dice_light else R.color.dice_dark
                    )
                )
                // Set text to roll number.
                it.text = roll.toString()
            }

            diceView.rotation = (-30..30).random().toFloat()

            val params = diceView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(params.leftMargin, (-100..50).random(), params.rightMargin, params.bottomMargin)
            diceView.layoutParams = params
        }
    }
}