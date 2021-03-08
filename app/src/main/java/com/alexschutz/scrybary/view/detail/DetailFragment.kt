package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentDetailsFullBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.view.detail.cardimage.ImageDialogFragment
import com.alexschutz.scrybary.viewmodel.DetailViewModel

class DetailFragment : BackButtonFragment()  {

    private lateinit var binding: FragmentDetailsFullBinding

    private lateinit var viewModel: DetailViewModel

    private val rulingsListAdapter = RulingsListAdapter(arrayListOf())
    private val legalityListAdapter = LegalityListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentDetailsFullBinding.inflate(inflater, container, false)

        binding.backListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        arguments?.let {
            viewModel.card.value = DetailFragmentArgs.fromBundle(it).card
        }

        viewModel.fetchCardDetail()

        viewModel.cardFront.observe(viewLifecycleOwner, { front ->
            with (binding) {
                this.front = front

                if (front?.power != null || front?.toughness != null)
                    tvSlash.visibility = View.VISIBLE
                else tvSlash.visibility = View.GONE
            }
        })

        viewModel.cardBack.observe(viewLifecycleOwner, { back ->
            with (binding) {
                this.back = back

                clBack.visibility = if (back == null) View.GONE else View.VISIBLE

                tvBackSlash.visibility =
                    if (back?.power != null || back?.toughness != null) View.VISIBLE
                    else View.GONE
            }
        })

        viewModel.cardFrontImageUri.observe(viewLifecycleOwner, { uri ->
            binding.frontImageUri = uri
        })

        viewModel.cardBackImageUri.observe(viewLifecycleOwner, { uri ->
            binding.backImageUri = uri
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

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.nestedScrollView.visibility =  if (it) View.GONE else View.VISIBLE
                binding.loadBar.visibility = if (it) View.VISIBLE else View.GONE

            }
        })

        // TODO view bind :^)
        viewModel.printUri.observe(viewLifecycleOwner, { id ->
            binding.ivCardImage.setOnClickListener {
                ImageDialogFragment(id, true).show(childFragmentManager, "dialog")
            }
            binding.ivBackCardImage.setOnClickListener {
                ImageDialogFragment(id, false).show(childFragmentManager, "dialog")
            }
        })
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_detailFragment_to_libraryFragment)
    }
}