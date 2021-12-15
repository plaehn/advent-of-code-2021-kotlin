package org.plaehn.adventofcode.day15

import org.plaehn.adventofcode.common.Matrix

class Chiton(private val riskLevelMap: Matrix<Int>) {

    fun computeLowestTotalRisk(): Int =
        TODO()

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            Chiton(
                Matrix.fromRows(
                    rows = inputLines.map { inputLine -> inputLine.map { it.digitToInt() } },
                    defaultValue = 0
                )
            )
    }
}
