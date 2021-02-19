package com.alexschutz.scrybary

fun roll(number: Int, sides: Int): RollTotal {
    val total = RollTotal()
    for (i in 1..number) {
        val r = (1..sides).random()
        total.rolls.add(r)
        total.sum += r
    }

    return total
}

public data class RollTotal(
    val rolls: ArrayList<Int> = arrayListOf(),
    var sum: Int = 0
)

