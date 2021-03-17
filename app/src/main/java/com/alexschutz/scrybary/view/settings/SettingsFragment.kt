package com.alexschutz.scrybary.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.PreferenceFragmentCompat
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentSettingsBinding
import com.alexschutz.scrybary.view.BackButtonFragment

class SettingsFragment : BackButtonFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val preferences =
            requireContext().getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.backListener = this

        // TODO view bind
        binding.layoutStartingLifeTotal.etLifeTotal
            .setText(
                (preferences.getInt(getString(R.string.starting_life_total), 20)).toString()
            )

        binding.clearSearchHistory.setOnClickListener {

            // Clear search history
            preferences
                .edit()
                .putStringSet(getString(R.string.search_history), setOf())
                .apply()

            Toast.makeText(context, "Search history cleared.", Toast.LENGTH_SHORT).show()
        }

        binding.layoutStartingLifeTotal.etLifeTotal.doOnTextChanged { text, start, before, count ->

            if (text?.isNotEmpty() == true) {

                preferences
                    .edit()
                    .putInt(getString(R.string.starting_life_total), text.toString().toInt())
                    .apply()

                resetCounterTotals()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_menuFragment)
    }

    private fun resetCounterTotals() {

        val preferences =
            requireContext().getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

        // TODO find a place to combine this with the part in MainActivity that does the same.
        with(preferences.edit()) {
            val counterKeys = arrayListOf(
                getString(R.string.p1_life),
                getString(R.string.p1_box_1),
                getString(R.string.p1_box_2),
                getString(R.string.p1_box_3),
                getString(R.string.p2_life),
                getString(R.string.p2_box_1),
                getString(R.string.p2_box_2),
                getString(R.string.p2_box_3)
            )

            // Life totals default to 20, everything else defaults to 0.
            for (key in counterKeys)
                putInt(
                    key,
                    if (key == getString(R.string.p1_life) || key == getString(R.string.p2_life))
                        preferences.getInt(getString(R.string.starting_life_total), 20)
                    else 0
                )

            apply()
        }
    }
}