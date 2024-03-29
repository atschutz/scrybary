package com.alexschutz.scrybary.view.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentSettingsBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import com.alexschutz.scrybary.view.MainActivity

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
        binding.clearSearchHistory.setOnClickListener {

            // Clear search history
            preferences
                .edit()
                .putStringSet(getString(R.string.pref_search_history), setOf())
                .apply()

            val toast = Toast.makeText(context, "Search history cleared.", Toast.LENGTH_SHORT)

            toast.cancel()
            toast.show()
        }

        with (binding.layoutStartingLifeTotal.etLifeTotal) {

            setText(
                (preferences.getInt(getString(R.string.pref_starting_life_total), 20)).toString()
            )

            doOnTextChanged { text, _, _, _ ->

                if (text?.isNotEmpty() == true) {

                    preferences
                        .edit()
                        .putInt(getString(R.string.pref_starting_life_total), text.toString().toInt())
                        .apply()

                    resetCounterTotals()
                }
            }
        }

        with (binding.layoutNumberOfPlayers.rgPlayers) {

            // Check current preferred number of players, default 2.
            (getChildAt(preferences.getInt(
                getString(R.string.pref_number_of_players), 2) - 1) as RadioButton).isChecked = true

            // Change preference when number of players is changed.
            setOnCheckedChangeListener { group, checkedId ->
                preferences
                    .edit()
                    .putInt(
                        getString(R.string.pref_number_of_players),
                        group.indexOfChild(view?.findViewById<RadioButton>(checkedId)) + 1)
                    .apply()
            }
        }

        with (binding.layoutKeepScreenOn.swKeepScreenOn) {

            isChecked = preferences.getBoolean(getString(R.string.pref_keep_screen_on), false)

            setOnCheckedChangeListener { _, isChecked ->
                preferences
                    .edit()
                    .putBoolean(
                        getString(R.string.pref_keep_screen_on),
                        isChecked
                    )
                    .apply()
            }
        }

        with (binding.layoutReroll.swReroll) {

            isChecked = preferences.getBoolean(getString(R.string.pref_reroll_if_tie), false)

            setOnCheckedChangeListener { _, isChecked ->
                preferences.edit().putBoolean(getString(R.string.pref_reroll_if_tie), isChecked).apply()
            }
        }

        binding.llSendFeedback.setOnClickListener {

            // Create intent to open Email app.
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("manadorkapp@gmail.com"))
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
        }

        return binding.root
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

            // Life totals default to 20, everything else defaults to 0.
            for (key in (activity as MainActivity).lifeKeys)
                putInt(key, preferences.getInt(getString(R.string.pref_starting_life_total), 20))

            for (key in (activity as MainActivity).counterKeys) putInt(key, 0)

            apply()
        }
    }
}