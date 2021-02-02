package com.alexschutz.scrybary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentLibraryBinding
import com.alexschutz.scrybary.databinding.FragmentMenuBinding

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }
}