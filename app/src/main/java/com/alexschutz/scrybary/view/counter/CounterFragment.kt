package com.alexschutz.scrybary.view.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentCounterBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import kotlin.random.Random

class CounterFragment : BackButtonFragment() {

    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCounterBinding.inflate(inflater, container, false)

        binding.listener = this

        with(binding) {

            // Quick and dirty way to set all the keys and system preferences.
            bottomLifeCounter.setButtonsWithKey(getString(R.string.p1_life))
            topLifeCounter.setButtonsWithKey(getString(R.string.p2_life))

            for (child in bottomBtnContainer.findViewById<LinearLayout>(R.id.ll_button_holder).children) {
                if (child.id == R.id.start_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p1_box_1))
                if (child.id == R.id.middle_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p1_box_2))
                if (child.id == R.id.end_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p1_box_3))
            }

            for (child in topBtnContainer.findViewById<LinearLayout>(R.id.ll_button_holder).children) {
                if (child.id == R.id.start_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p2_box_1))
                if (child.id == R.id.middle_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p2_box_2))
                if (child.id == R.id.end_counter) (child as CounterButton).setButtonsWithKey(getString(R.string.p2_box_3))
            }

            btnRefresh.setOnClickListener {
                topBtnContainer.refresh()
                bottomBtnContainer.refresh()
                topLifeCounter.refresh()
                bottomLifeCounter.refresh()
            }

            btnRoll.setOnClickListener {
                val result = if (Random.nextBoolean()) "Heads" else "Tails"
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_counterFragment_to_menuFragment)
    }
}