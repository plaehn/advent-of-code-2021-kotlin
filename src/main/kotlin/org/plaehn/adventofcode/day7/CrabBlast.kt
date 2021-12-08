package org.plaehn.adventofcode.day7

import org.plaehn.adventofcode.common.toIntList
import kotlin.math.absoluteValue

class CrabBlast(
    private val initialPositions: List<Int>
) {

    private val range = initialPositions.minOrNull()!!..initialPositions.maxOrNull()!!

    fun findMinimalFuelConsumption(isLinearCostFunction: Boolean = true) =
        range.minOf { pos ->
            costOfAligningTo(
                pos,
                if (isLinearCostFunction) {
                    { it }
                } else {
                    { it * (1 + it) / 2 }
                }
            )
        }

    private fun costOfAligningTo(pos: Int, distance2Cost: (Int) -> Int) =
        initialPositions
            .map { (it - pos).absoluteValue }
            .sumOf(distance2Cost)

    companion object {
        fun fromInputString(input: String) = CrabBlast(input.toIntList())
    }
}
