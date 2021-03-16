package com.alexschutz.scrybary.view.library

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexschutz.scrybary.*
import com.alexschutz.scrybary.databinding.FragmentLibraryBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.viewmodel.LibraryViewModel


class LibraryFragment : BackButtonFragment(), SearchClickListener {

    // TODO fix big margin under text when P/T or loyalty or flavor text is missing.

    private lateinit var binding: FragmentLibraryBinding

    private lateinit var viewModel: LibraryViewModel
    private val cardListAdapter = CardListAdapter(arrayListOf())

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        preferences = requireContext().getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

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

        binding.actvSearch.setOnEditorActionListener { _, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_NEXT -> {
                    onSearchClicked(view)
                    true
                } else -> false
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_search_history_dropdown,
            preferences.getStringSet(
                    getString(R.string.search_history),
                    setOf())?.toTypedArray() ?: arrayOf()
        )

        binding.actvSearch.setAdapter(adapter)

        viewModel.cards.observe(viewLifecycleOwner, { cards ->
            cards?.let {
                cardListAdapter.updateCardList(cards)

                // Reset recycler view to top position.
                binding.cardList.scrollToPosition(0)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.loadBar.visibility = if (it) View.VISIBLE else View.GONE

                // Once we start loading, hide the logo and never show it again until fragment is
                // reloaded.
                binding.ivLogo.visibility = View.GONE
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

        viewModel.search.value = binding.actvSearch.text.toString()
        viewModel.fetchFromRemote()

        binding.cardList.visibility = View.GONE

        var stringSet =
            preferences.getStringSet(getString(R.string.search_history), setOf())?.toMutableSet() ?: mutableSetOf()

        if ((viewModel.search.value)?.length ?: 0 >= 3 && !stringSet.contains(viewModel.search.value)) {

            with(preferences.edit()) {

                if (stringSet.size >= 10) stringSet = stringSet.drop(1).toMutableSet()
                stringSet.add(viewModel.search.value)

                putStringSet(getString(R.string.search_history), stringSet)

                apply()
            }
        }

        v.hideKeyboard()
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_libraryFragment_to_menuFragment)
    }
}