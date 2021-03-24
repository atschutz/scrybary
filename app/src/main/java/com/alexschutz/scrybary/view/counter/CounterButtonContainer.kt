package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.alexschutz.scrybary.R

class CounterButtonContainer(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    val counterStart: CounterButton
    val counterMiddle: CounterButton
    val counterEnd: CounterButton

    init {
        inflate(context, R.layout.counter_button_container, this)

        counterStart = findViewById(R.id.start_counter)
        counterMiddle = findViewById(R.id.middle_counter)
        counterEnd = findViewById(R.id.end_counter)
    }

    fun refresh() {
        counterStart.refresh()
        counterMiddle.refresh()
        counterEnd.refresh()
    }
}
