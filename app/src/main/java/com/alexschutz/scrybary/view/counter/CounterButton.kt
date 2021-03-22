package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.alexschutz.scrybary.R

class CounterButton(context: Context, attrs: AttributeSet) : Counter(context, attrs) {

    private val counterMinus: AppCompatButton
    private val counterPlus: AppCompatButton

    init {
        inflate(context, R.layout.counter_button, this)

        // If we have 4 players, half text sizes for all TextViews and EditTexts.
        if (context.getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)
                .getInt(context.getString(R.string.number_of_players), 2) == 4) {

            for (child in children) {

                if (child is TextView) child.textSize = child.textSize / 2
                if (child is EditText) child.textSize = child.textSize / 2
            }
        }

        counterMinus = findViewById(R.id.btn_minus)
        counterPlus = findViewById(R.id.btn_plus)

        number = findViewById(R.id.number)
    }

    fun setButtonsWithKey(keyString: String) {
        key = keyString
        setKeyAndButtons(key, counterMinus, counterPlus, null, null)
    }
}