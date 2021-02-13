package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentDetailBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : BackButtonFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var cardId = "no id!"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.listener = this
        binding.vpDetail.adapter =
            DetailFragmentStateAdapter(this.childFragmentManager, this.lifecycle)

        val tabIcons = listOf(R.drawable.ic_card, R.drawable.ic_info, R.drawable.ic_rulings)

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, position ->
            tab.icon = ResourcesCompat.getDrawable(resources, tabIcons[position], null)
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cardId = DetailFragmentArgs.fromBundle(it).cardId
        }
    }
}