package com.alexschutz.scrybary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentDiceBinding
import com.alexschutz.scrybary.databinding.FragmentLibraryBinding

class DiceFragment : Fragment() {

    private var _binding: FragmentDiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDiceBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }
}