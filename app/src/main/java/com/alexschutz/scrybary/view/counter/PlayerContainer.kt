package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexschutz.scrybary.R

class PlayerContainer(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs)
{

    val lifeCounter: LifeCounter
    val buttonContainer: CounterButtonContainer

    init {

        inflate(context, R.layout.player_container, this)

        lifeCounter = findViewById(R.id.life_counter)
        buttonContainer = findViewById(R.id.btn_container)
    }

    fun refresh() {
        lifeCounter.refresh()
        buttonContainer.refresh()
    }

    fun scaleTextSizeWhenThreeOrMorePlayers() {
        lifeCounter.scaleTextSizeWhenThreeOrMorePlayers()
        buttonContainer.scaleTextSizeForThreeOrMorePlayers()
    }

    fun setKeys(life: Int, k1: Int, k2: Int, k3: Int) {
        lifeCounter.setButtonsWithKey(resources.getString(life))
        buttonContainer.setKeys(k1, k2, k3)
    }
}