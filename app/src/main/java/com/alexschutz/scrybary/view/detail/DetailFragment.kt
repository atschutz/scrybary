package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentDetailBinding
import com.alexschutz.scrybary.databinding.FragmentDetailsFullBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : BackButtonFragment()  {

    // TODO add 4th fragment for back face info. Hide if no back face.

    private lateinit var binding: FragmentDetailsFullBinding

    private lateinit var viewModel: DetailViewModel

    private val rulingsListAdapter = RulingsListAdapter(arrayListOf())
    private val legalityListAdapter = LegalityListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentDetailsFullBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        arguments?.let {
            viewModel.card.value = DetailFragmentArgs.fromBundle(it).card
        }

        viewModel.fetchCardDetail()
        viewModel.fetchCardRulings()

        viewModel.card.observe(viewLifecycleOwner, { card ->
            binding.card = card
        })

        viewModel.cardDetail.observe(viewLifecycleOwner, { detail ->
            binding.detail = detail
        })

        viewModel.cardImageUri.observe(viewLifecycleOwner, { uri ->
            binding.imageUri = uri
        })

        binding.rulingsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rulingsListAdapter
        }

        binding.legalityList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = legalityListAdapter
        }

        viewModel.rulings.observe(viewLifecycleOwner, { rulings ->
            rulings?.let {
                rulingsListAdapter.updateRulingsList(rulings)
            }
        })

        viewModel.legalities.observe(viewLifecycleOwner, { legalities ->
            legalities?.let {
                legalityListAdapter.updateLegalityList(legalities)
            }
        })

        // Show slash if card has power or toughness. Otherwise hide.
        with (binding) {
            if (card?.power != null || card?.toughness != null)
                tvSlash.visibility = View.VISIBLE
            else tvSlash.visibility = View.GONE
        }
    }
}