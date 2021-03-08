package com.alexschutz.scrybary.view.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentCounterBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import kotlin.random.Random

class CounterFragment : BackButtonFragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentCounterBinding.inflate(inflater, container, false)

        binding.listener = this

        val view = binding.root

        with(binding) {
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

        return view
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_counterFragment_to_menuFragment)
    }
}