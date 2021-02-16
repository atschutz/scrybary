package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.alexschutz.scrybary.databinding.FragmentInfoBinding
import com.alexschutz.scrybary.model.Card
import com.alexschutz.scrybary.viewmodel.DetailViewModel

class InfoFragment() : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        Log.d("infofragment", "infofragment created")

        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO bind viewmodel to XML so field setting doesn't have to be done in here.

        viewModel =
            ViewModelProvider(parentFragment as ViewModelStoreOwner).get(DetailViewModel::class.java)

        viewModel.card.observe(viewLifecycleOwner, Observer { card ->
            binding.card = card
        })

        viewModel.cardDetail.observe(viewLifecycleOwner, Observer { detail ->
            binding.detail = detail
        })

    }
}