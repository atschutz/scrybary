package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import com.alexschutz.scrybary.R

// TODO add long press to change number and smaller font size with more digits.

class LifeCounter(context: Context, attrs: AttributeSet) : Counter(context, attrs) {

    private val lifeMinus: AppCompatButton
    private val lifePlus: AppCompatButton
    private val lifeMinus5: AppCompatButton
    private val lifePlus5: AppCompatButton

    init {

        inflate(context, R.layout.life_counter, this)

        // If we have 4 players, half text sizes for all TextViews and EditTexts.
        if (context.getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
                .getInt(context.getString(R.string.number_of_players), 2) == 4) {

            for (child in children) {

                if (child is TextView) child.textSize = child.textSize / 2
                if (child is EditText) child.textSize = child.textSize / 2
            }
        }

        lifeMinus = findViewById(R.id.btn_life_minus)
        lifePlus = findViewById(R.id.btn_life_plus)
        lifeMinus5 = findViewById(R.id.btn_minus_5)
        lifePlus5 = findViewById(R.id.btn_plus_5)

        number = findViewById(R.id.life_number)
    }

    // Lets us set the key from the fragment.
    fun setButtonsWithKey(keyString: String) {
        key = keyString
        setKeyAndButtons(key, lifeMinus, lifePlus, lifeMinus5, lifePlus5)
    }
}