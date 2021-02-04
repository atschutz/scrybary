package com.alexschutz.scrybary.view.counter

import android.widget.Button
import android.widget.TextView

interface Counter {

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