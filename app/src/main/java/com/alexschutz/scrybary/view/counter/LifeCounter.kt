package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

// TODO add long press to change number and smaller font size with more digits.

class LifeCounter(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), Counter {

    override var value = 0
    override lateinit var number: TextView

    init {

        inflate(context, R.layout.life_counter, this)

        val lifeMinus = findViewById<AppCompatButton>(R.id.btn_life_minus)
        val lifePlus = findViewById<AppCompatButton>(R.id.btn_life_plus)
        number = findViewById(R.id.life_number)

        setButtons(lifeMinus, lifePlus)
    }
}