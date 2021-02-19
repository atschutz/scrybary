package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.databinding.FragmentRulingsBinding
import com.alexschutz.scrybary.viewmodel.DetailViewModel

class RulingsFragment : Fragment() {

    private lateinit var binding: FragmentRulingsBinding

    private lateinit var viewModel: DetailViewModel
    private val rulingsListAdapter = RulingsListAdapter(arrayListOf())
    private val legalityListAdapter = LegalityListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentRulingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(parentFragment as ViewModelStoreOwner).get(DetailViewModel::class.java)

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
    }
}