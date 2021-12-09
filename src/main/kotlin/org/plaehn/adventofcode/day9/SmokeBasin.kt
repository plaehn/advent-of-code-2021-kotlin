package org.plaehn.adventofcode.day9

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.product

class SmokeBasin(
    private val heightmap: Matrix<Int>
) {

    fun computeProductOfSizeOfThreeLargestBasins() =
        findLowPoints()
            .map { findBasin(it.key, it.value) }
            .map { it.size }
            .sortedByDescending { it }
            .take(3)
            .product()

    private fun findBasin(coord: Coord, height: Int): MutableSet<Coord> {
        val basin = mutableSetOf(coord)
        heightmap.neighbors(coord).forEach { neighbor ->
            val neighborHeight = heightmap[neighbor.y][neighbor.x]
            if (!basin.contains(neighbor) && neighborHeight > height && neighborHeight != 9) {
                basin += neighbor
                basin += findBasin(neighbor, neighborHeight)
            }
        }
        return basin
    }

    fun computeSumOfRiskLevels() =
        findLowPoints()
            .map {
                1 + it.value
            }.sum()

    private fun findLowPoints() =
        heightmap.toMap().filter { entry -> isLowPoint(entry.key) }

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
