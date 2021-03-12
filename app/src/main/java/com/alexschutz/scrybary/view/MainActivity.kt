package com.alexschutz.scrybary.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
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

        val preferences = getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

        with (preferences.edit()) {

            val keys = arrayListOf(
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
            for (key in keys )
                if (!preferences.contains(key))
                    putInt(
                        key,
                        if (key == getString(R.string.p1_life) || key == getString(R.string.p2_life)) 20
                        else 0
                    )

            apply()
        }
    }
}