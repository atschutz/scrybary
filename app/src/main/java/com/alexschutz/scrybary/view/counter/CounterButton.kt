package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

class CounterButton(context: Context, attrs: AttributeSet) : Counter(context, attrs) {

    private val counterMinus: AppCompatButton
    private val counterPlus: AppCompatButton

    init {
        inflate(context, R.layout.counter_button, this)

        counterMinus = findViewById(R.id.btn_minus)
        counterPlus = findViewById(R.id.btn_plus)

        number = findViewById(R.id.number)
    }

    fun setButtonsWithKey(keyString: String) {
        key = keyString
        setKeyAndButtons(key, counterMinus, counterPlus, null, null)
    }
}