package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.databinding.FragmentRulingsBinding
import com.alexschutz.scrybary.viewmodel.DetailViewModel

class RulingsFragment : Fragment() {

    private var _binding: FragmentRulingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel
    private val rulingsListAdapter = RulingsListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentRulingsBinding.inflate(inflater, container, false)

        Log.d("rulingsfragment", "rulingsfragment created")

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

        viewModel.rulings.observe(viewLifecycleOwner, { rulings ->
            rulings?.let {
                rulingsListAdapter.updateRulingsList(rulings)
            }
        })
    }
}