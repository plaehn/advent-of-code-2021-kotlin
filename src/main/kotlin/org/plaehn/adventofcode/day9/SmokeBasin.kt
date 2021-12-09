package org.plaehn.adventofcode.day9

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix

class SmokeBasin(
    private val heightmap: Matrix<Int>
) {

    fun computeSumOfRiskLevels() =
        heightmap.toMap()
            .filter { entry ->
                isLowPoint(entry.key)
            }.map {
                1 + it.value
            }.sum()

    private fun isLowPoint(coord: Coord) =
        heightmap
            .neighbors(coord)
            .all { neighbor -> heightmap[coord.y][coord.x] < heightmap[neighbor.y][neighbor.x] }

    companion object {
        fun fromInputLines(inputLines: List<String>): SmokeBasin {
            val rows = inputLines.map { inputLine ->
                inputLine.toList().map { it.digitToInt() }
            }
            return SmokeBasin(Matrix.fromRows(rows, -1))
        }
    }
}
