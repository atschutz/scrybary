package com.alexschutz.scrybary.view.detail.cardimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexschutz.scrybary.databinding.FragmentCardImageBinding

class CardImageFragment(private val url: String?) : Fragment() {

    private lateinit var binding: FragmentCardImageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCardImageBinding.inflate(inflater, container, false)

        binding.imageUrl = url

        binding.root.setOnClickListener { (parentFragment as ImageDialogFragment).dismiss() }

        return binding.root

    }
}