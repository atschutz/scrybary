package com.alexschutz.scrybary.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexschutz.scrybary.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        setCounterDefaults()
    }

    fun setCounterDefaults() {

        val preferences = getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

        with (getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE).edit()) {

            val lifeKeys = arrayListOf(
                getString(R.string.p1_life),
                getString(R.string.p2_life),
                getString(R.string.p3_life),
                getString(R.string.p4_life),
            )

            val counterKeys = arrayListOf(
                getString(R.string.p1_box_1),
                getString(R.string.p1_box_2),
                getString(R.string.p1_box_3),
                getString(R.string.p2_box_1),
                getString(R.string.p2_box_2),
                getString(R.string.p2_box_3),
                getString(R.string.p3_box_1),
                getString(R.string.p3_box_2),
                getString(R.string.p3_box_3),
                getString(R.string.p4_box_1),
                getString(R.string.p4_box_2),
                getString(R.string.p4_box_3)
            )

            // Life totals default to starting life total pref, counter buttons default to 0.
            for (key in lifeKeys ) if (!preferences.contains(key))
                putInt(key, preferences.getInt(getString(R.string.pref_starting_life_total), 20))

            for (key in counterKeys ) if (!preferences.contains(key)) putInt(key, 0)

            apply()
        }
    }
}