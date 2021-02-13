package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.databinding.FragmentRulingsBinding

class RulingsFragment : Fragment() {

    private var _binding: FragmentRulingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentRulingsBinding.inflate(inflater, container, false)

        Log.d("rulingsfragment", "rulingsfragment created")

        return binding.root
    }
}