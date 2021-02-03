package com.alexschutz.scrybary.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

class CounterButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.counter_button, this)

        val number = findViewById<TextView>(R.id.number)
        val plus = findViewById<TextView>(R.id.plus)
        val minus = findViewById<TextView>(R.id.minus)

        var value = 0

        number.text = value.toString()

        plus.setOnClickListener {
            value++
            number.text = value.toString()
        }

        minus.setOnClickListener {
            value--
            number.text = value.toString()
        }
    }
}