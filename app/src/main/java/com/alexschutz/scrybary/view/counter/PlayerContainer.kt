package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

class PlayerContainer(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs)
{
    init {
        inflate(context, R.layout.player_container, this)
    }
}