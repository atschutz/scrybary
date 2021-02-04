package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

class CounterButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), Counter {

    override var value = 0
    override lateinit var number: TextView

    init {
        
        inflate(context, R.layout.counter_button, this)

        val counterMinus = findViewById<AppCompatButton>(R.id.btn_minus)
        val counterPlus = findViewById<AppCompatButton>(R.id.btn_plus)
        number = findViewById(R.id.number)

        setButtons(counterMinus, counterPlus)
    }
}