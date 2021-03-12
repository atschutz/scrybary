package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Button
import android.widget.TextView
import com.alexschutz.scrybary.R

interface Counter {

    // TODO make this a class that CounterButton and LifeCounter extend.
    // TODO bind on click
    val number: TextView
    var key: String

    fun setKeyAndButtons(key: String, minus: Button, plus: Button, minus5: Button?, plus5: Button?) {

        var value = number.context
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
        updatePrefs(
            if (key == number.context.getString(R.string.p1_life)
                || key == number.context.getString(R.string.p2_life)) 20
            else 0
        )

        number.text = number.context
            .getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
            .getInt(key, 0)
            .toString()
    }

    fun updatePrefs(value: Int) {
        number.context
            .getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
            .edit()
            .putInt(key, value)
            .apply()
    }
}