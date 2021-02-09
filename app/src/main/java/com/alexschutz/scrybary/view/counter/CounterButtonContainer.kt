package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.alexschutz.scrybary.R

class CounterButtonContainer(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.counter_button_container, this)
    }

    fun refresh() {
        findViewById<CounterButton>(R.id.start_counter).refresh()
        findViewById<CounterButton>(R.id.middle_counter).refresh()
        findViewById<CounterButton>(R.id.end_counter).refresh()
    }
}
