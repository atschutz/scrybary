package com.alexschutz.scrybary.view.library

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.*
import com.alexschutz.scrybary.databinding.FragmentLibraryBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.viewmodel.LibraryViewModel
import kotlin.concurrent.thread

class LibraryFragment : BackButtonFragment(), SearchClickListener {

    // TODO fix big margin under text when P/T or loyalty or flavor text is missing.

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

        binding.backListener = this
        binding.searchListener = this

        binding.cardList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardListAdapter
        }

        binding.tvSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSearchClicked(view)
                true
            } else {
                false
            }
        }

        viewModel.cards.observe(viewLifecycleOwner, { cards ->
            cards?.let {
                cardListAdapter.updateCardList(cards)

                // Reset recycler view to top position.
                binding.cardList.scrollToPosition(0)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.cardList.visibility =  if (it) View.GONE else View.VISIBLE
                binding.loadBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.hasNoResults.observe(viewLifecycleOwner, { hasNoResults ->
            hasNoResults?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.ivNarrow.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.hasError.observe(viewLifecycleOwner, { hasNoResults ->
            hasNoResults?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.ivError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onSearchClicked(v: View) {

        viewModel.search.value = binding.tvSearch.text.toString()
        viewModel.fetchFromRemote()

        binding.cardList.visibility = View.GONE

        v.hideKeyboard()
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_libraryFragment_to_menuFragment)
    }
}