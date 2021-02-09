package com.alexschutz.scrybary.view.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.databinding.FragmentCounterBinding
import kotlin.random.Random

class CounterFragment : Fragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentCounterBinding.inflate(inflater, container, false)

        val view = binding.root

        with(binding) {

            btnBack.setOnClickListener {
                activity?.onBackPressed()
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

        return view
    }
}