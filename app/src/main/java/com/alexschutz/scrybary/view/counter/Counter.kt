package com.alexschutz.scrybary.view.counter

import android.widget.Button
import android.widget.TextView

interface Counter {

    // TODO make this a class that CounterButton and LifeCounter extend.
    // TODO bind on click

    var value: Int
    val number: TextView

    fun setButtons(minus: Button, plus: Button) {

        refresh()

        plus.setOnClickListener {
            value++
            number.text = value.toString()
        }

        minus.setOnClickListener {
            value--
            number.text = value.toString()
        }
    }

    fun refresh() {
        value = 0
        number.text = value.toString()
    }
}