package com.alexschutz.scrybary.view.library

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.databinding.FragmentLibraryBinding
import com.alexschutz.scrybary.hideKeyboard
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.viewmodel.LibraryViewModel

class LibraryFragment : BackButtonFragment() {

    private lateinit var binding: FragmentLibraryBinding

    private lateinit var viewModel: LibraryViewModel
    private val cardListAdapter = CardListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)

        binding.listener = this
        binding.viewModel = viewModel

        binding.cardList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardListAdapter
        }

        binding.tvSearch.doAfterTextChanged {
            viewModel.search.value = binding.tvSearch.text.toString()
        }

        binding.tvSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.hideKeyboard()
                true
            } else {
                false
            }
        }

        viewModel.cards.observe(viewLifecycleOwner, { cards ->
            cards?.let {
                cardListAdapter.updateCardList(cards)
            }
        })
    }
}