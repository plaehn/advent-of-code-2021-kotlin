package org.plaehn.adventofcode.day7

import org.plaehn.adventofcode.common.toIntList
import kotlin.math.absoluteValue

class CrabBlast(
    private val initialPositions: List<Int>
) {

    fun findMinimalFuelConsumption() = initialPositions.minOf { costOfAligningTo(it) }

    private fun costOfAligningTo(pos: Int) = initialPositions.sumOf { (it - pos).absoluteValue }

    companion object {
        fun fromInputString(input: String) = CrabBlast(input.toIntList())
    }
}
