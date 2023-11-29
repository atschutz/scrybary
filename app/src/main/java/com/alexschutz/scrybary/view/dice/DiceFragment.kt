package com.alexschutz.scrybary.view.dice

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import com.alexschutz.scrybary.*
import com.alexschutz.scrybary.databinding.FragmentDiceBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.google.android.flexbox.FlexboxLayout
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class DiceFragment : BackButtonFragment(), RollClickListener, ShakeListener {

    private lateinit var binding: FragmentDiceBinding

    private var sensorManager: SensorManager? = null

    private var numberOfPlayers = 2
    private var numberOfDice = 2
    private var diceSides = 6

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            diceSides = if (index < 5) { (index + 2) * 2 } else { 20 }
        }

        binding.tvRollHint.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shrink_grow))

        // Register shake listener.
        sensorManager = getSystemService(requireContext(), SensorManager::class.java)

        toggleShakeSensor(true)

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_diceFragment_to_menuFragment)
    }

    override fun shakeDetected() {
        toggleShakeSensor(false)
        doRollAndUpdateView()

        // Put a delay on turning the shake listener back on to prevent multiple rolls in one shake.
        Timer().schedule(500) {
            toggleShakeSensor(true)
        }
    }

    override fun onRollClicked(v: View) { doRollAndUpdateView() }

    private fun toggleShakeSensor(isActive: Boolean) {

        if (isActive) {
            sensorManager?.registerListener(
                this,
                sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            sensorManager?.unregisterListener(this)
        }
    }

    private fun doRollAndUpdateView() {

        val id = when (diceSides) {
            // Assign respective layout ID.
            4 -> R.layout.d4_layout
            6 -> R.layout.d6_layout
            8 -> R.layout.d8_layout
            10 -> R.layout.d10_layout
            12 -> R.layout.d12_layout
            20 -> R.layout.d20_layout

            // This should never get reached.
            else -> R.layout.d6_layout
        }

        with(binding) {
            // Hide the roll hint
            tvRollHint.animation = null
            tvRollHint.visibility = View.GONE

            p1DiceContainer.removeAllViews()
            p2DiceContainer.removeAllViews()

            // Player 1 always rolls. Do roll and make visible.
            p1Roll = roll(numberOfDice, diceSides)
            p1TotalContainer.visibility = View.VISIBLE

            drawDice(p1Roll?.rolls ?: arrayListOf(), p1DiceContainer, 1, id)

            // If we have more than one player, make player 2 total visible and roll.
            // Else, hide player 2 total and set values to 0
            p2Roll =
                if (numberOfPlayers > 1) {

                    var rollTotal = roll(numberOfDice, diceSides)

                    if (requireContext().getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
                            .getBoolean(getString(R.string.reroll_if_tie), false)) {
                        while (rollTotal.sum == p1Roll?.sum) {
                            rollTotal = roll(numberOfDice, diceSides)
                        }
                    }

                    p2TotalContainer.visibility = View.VISIBLE
                    p2DiceContainer.visibility = View.VISIBLE

                    drawDice(rollTotal.rolls, p2DiceContainer, 2, id)

                    rollTotal
                } else {

                    p2TotalContainer.visibility = View.GONE
                    p2DiceContainer.visibility = View.GONE

                    RollTotal()
                }

            // White the winning player's total, grey the loser's. If it's a tie, white both.
            tvP1Total.setTextColor(
                ContextCompat.getColor(
                    tvP1Total.context,
                    if ((p1Roll?.sum ?: 0) < (p2Roll?.sum ?: 0)) R.color.dimmed_grey else R.color.white
                )
            )
            tvP2Total.setTextColor(
                ContextCompat.getColor(
                    tvP2Total.context,
                    if ((p1Roll?.sum ?: 0) > (p2Roll?.sum ?: 0)) R.color.dimmed_grey else R.color.white
                )
            )

            tvP1Total.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            tvP2Total.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))

            view?.invalidate()
        }
    }

    // TODO add dynamic sizing.
    private fun drawDice(rolls: ArrayList<Int>, container: FlexboxLayout, player: Int, layoutId: Int) {

        container.removeAllViews()

        for (roll in rolls) {

            // Inflate dice view and add to container layout.
            val diceView = View.inflate(context, layoutId, null)
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
                // Set text to roll number.st
                it.text = roll.toString()
            }

            // Randomize rotation and position of dice.
            diceView.rotation = (-30..30).random().toFloat()

            val params = diceView.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(
                params.leftMargin,
                (-10..10).random(),
                dpToPx(10),
                params.bottomMargin
            )
            diceView.layoutParams = params

            // Animate dice roll.
            val anim = AnimationUtils.loadAnimation(context, R.anim.dice_roll)
            anim.startTime = 0
            anim.startOffset = (0..rolls.size*40).random().toLong()
            diceView.visibility = View.VISIBLE
            diceView.startAnimation(anim)
        }
    }
}