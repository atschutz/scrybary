package com.alexschutz.scrybary.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding.clearSearchHistory.setOnClickListener {

            // Clear search history
            preferences
                .edit()
                .putStringSet(getString(R.string.search_history), setOf())
                .apply()

            Toast.makeText(context, "Search history cleared.", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_menuFragment)
    }
}