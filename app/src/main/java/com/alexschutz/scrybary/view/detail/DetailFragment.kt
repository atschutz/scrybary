package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentDetailBinding
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : BackButtonFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

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

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        arguments?.let {
            viewModel.card.value = DetailFragmentArgs.fromBundle(it).card
        }

        viewModel.fetchCardDetail()

        viewModel.card.observe(viewLifecycleOwner, Observer { card ->
            (binding.vpDetail.adapter as DetailFragmentStateAdapter).card = card
        })
    }
}