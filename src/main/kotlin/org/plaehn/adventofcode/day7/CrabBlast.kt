package org.plaehn.adventofcode.day7

import org.plaehn.adventofcode.common.toIntList
import kotlin.math.absoluteValue

class CrabBlast(
    private val initialPositions: List<Int>
) {

    private val range = initialPositions.minOrNull()!!..initialPositions.maxOrNull()!!

    fun findHorizontalPosition() =
        range
            .map { costOfAligningTo(it) }
            .also { println(it) }
            .minByOrNull { it.first }!!
            .second

    private fun costOfAligningTo(pos: Int): Pair<Int, Int> =
        Pair(initialPositions.sumOf { (it - pos).absoluteValue }, pos)

    companion object {
        fun fromInputString(input: String) = CrabBlast(input.toIntList())
    }
}
