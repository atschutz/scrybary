package com.alexschutz.scrybary.view.library

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class LibraryFragment : BackButtonFragment(), SearchClickListener, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    // TODO fix big margin under text when P/T or loyalty or flavor text is missing.

    private lateinit var binding: FragmentLibraryBinding

    private lateinit var viewModel: LibraryViewModel
    private val cardListAdapter = CardListAdapter(arrayListOf())

    private lateinit var preferences: SharedPreferences
    private lateinit var watcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        binding.btnClear.setOnClickListener {
            viewModel.search.value = ""
            binding.actvSearch.text.clear()
        }

        watcher = object : TextWatcher {
            private var searchFor = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText

                launch {
                    delay(500L)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    onSearchClicked(view)
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        binding.actvSearch.addTextChangedListener(watcher)

        binding.actvSearch.setOnEditorActionListener { _, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_NEXT -> {
                    true
                } else -> false
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_search_history_dropdown,
            preferences.getStringSet(
                    getString(R.string.pref_search_history),
                    setOf())?.toTypedArray() ?: arrayOf()
        )

        binding.actvSearch.setAdapter(adapter)

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            cards?.let {
                cardListAdapter.updateCardList(cards)

                // Reset recycler view to top position.
                binding.cardList.scrollToPosition(0)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.loadBar.visibility = if (it) View.VISIBLE else View.GONE

                // Once we start loading, hide the logo and never show it again until fragment is
                // reloaded.
                binding.ivLogo.visibility = View.GONE
            }
        }

        viewModel.hasNoResults.observe(viewLifecycleOwner) { hasNoResults ->
            hasNoResults?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.ivNarrow.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewModel.hasError.observe(viewLifecycleOwner) { hasNoResults ->
            hasNoResults?.let {
                binding.cardList.visibility = if (it) View.GONE else View.VISIBLE
                binding.ivError.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onSearchClicked(v: View) {

        viewModel.search.value = binding.actvSearch.text.toString()
        viewModel.fetchFromRemote()

        binding.cardList.visibility = View.GONE

        // Add search to search history
        var stringSet =
            preferences.getStringSet(getString(R.string.pref_search_history), setOf())?.toMutableSet() ?: mutableSetOf()

        // Only add if length of search is >= 3 and not in search history already
        if (
            ((viewModel.search.value)?.length ?: 0) >= 3 &&
            !stringSet.contains(viewModel.search.value)
        ) {

            with(preferences.edit()) {

                // If search history set is at max capacity, remove the oldest one.
                if (stringSet.size >= resources.getInteger(R.integer.max_search_history))
                    stringSet = stringSet.drop(1).toMutableSet()

                stringSet.add(viewModel.search.value)

                putStringSet(getString(R.string.pref_search_history), stringSet)

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