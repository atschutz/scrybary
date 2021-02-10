package com.alexschutz.scrybary.view.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexschutz.scrybary.databinding.FragmentDetailBinding
import com.alexschutz.scrybary.view.BackButtonFragment

class DetailFragment : BackButtonFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.listener = this

        return view
    }
}