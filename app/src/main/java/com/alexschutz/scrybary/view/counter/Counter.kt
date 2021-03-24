package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

open class Counter(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    // TODO bind on click
    lateinit var number: TextView
    lateinit var key: String
    var value = 0

    fun setKeyAndButtons(key: String, minus: Button, plus: Button, minus5: Button?, plus5: Button?) {

        value = number.context
            .getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
            .getInt(key, 0)

        number.text = value.toString()

        plus.setOnClickListener {
            value++
            number.text = value.toString()
            updatePrefs(value)
        }

        minus.setOnClickListener {
            value--
            number.text = value.toString()
            updatePrefs(value)
        }

        plus5?.setOnClickListener {
            value += 5
            number.text = value.toString()
            updatePrefs(value)
        }

        minus5?.setOnClickListener {
            value -= 5
            number.text = value.toString()
            updatePrefs(value)
        }
    }

    fun refresh() {

        // Set value to 20 if it's a life counter, 0 otherwise.
        with (number.context) {
            value =
                if (key.contains("life")) {

                        getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
                            .getInt(getString(R.string.starting_life_total), 20)
                }
                else 0

            number.text = value.toString()
            updatePrefs(value)
        }
    }

    private fun updatePrefs(value: Int) {
        number.context
            .getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
            .edit()
            .putInt(key, value)
            .apply()
    }
}