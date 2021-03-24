package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.alexschutz.scrybary.R

// TODO add long press to change number and smaller font size with more digits.
class LifeCounter(context: Context, attrs: AttributeSet) : Counter(context, attrs) {

    private val lifeMinus: AppCompatButton
    private val lifePlus: AppCompatButton
    private val lifeMinus5: AppCompatButton
    private val lifePlus5: AppCompatButton

    init {

        inflate(context, R.layout.life_counter, this)

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

    fun scaleTextSizeWhen4Players() {

        with (findViewById<TextView>(R.id.life_number)) { setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize/4) }
        with (findViewById<TextView>(R.id.life_plus)) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize/4)
            setPadding(paddingLeft, paddingTop, 200, paddingBottom)
        }
        with (findViewById<TextView>(R.id.life_minus)) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize/4)
            setPadding(200, paddingTop, paddingRight, paddingBottom)
        }
        with (findViewById<AppCompatButton>(R.id.btn_plus_5)) { setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize/4) }
        with (findViewById<AppCompatButton>(R.id.btn_minus_5)) { setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize/4) }
    }
}