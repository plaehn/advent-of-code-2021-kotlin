package org.plaehn.adventofcode.day15

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import java.util.*


class Chiton(private val riskLevelMap: Matrix<Int>) {

    fun computeLowestTotalRisk(): Int {
        val destination = Coord(riskLevelMap.width() - 1, riskLevelMap.height() - 1)
        val toBeEvaluated = PriorityQueue<Traversal>().apply { add(Traversal(Coord(0, 0), 0)) }
        val visited = mutableSetOf<Coord>()

        while (toBeEvaluated.isNotEmpty()) {
            val thisPlace = toBeEvaluated.poll()
            when (thisPlace.coord) {
                destination -> return thisPlace.totalRisk
                !in visited -> {
                    visited.add(thisPlace.coord)
                    riskLevelMap
                        .neighbors(thisPlace.coord)
                        .forEach {
                            toBeEvaluated.offer(Traversal(it, thisPlace.totalRisk + riskLevelMap[it]))
                        }
                }
            }
        }
        error("No path to destination")
    }

    private class Traversal(val coord: Coord, val totalRisk: Int) : Comparable<Traversal> {
        override fun compareTo(other: Traversal) = this.totalRisk - other.totalRisk
    }

    companion object {
        fun fromInputLines(
            inputLines: List<String>,
            tileCount: Int = 1
        ): Chiton {
            val firstTileRow = inputLines.map { inputLine -> computeTileRow(tileCount, inputLine) }

            val tileRows = (1 until tileCount).fold(firstTileRow) { tileRow, tileNumber ->
                tileRow + firstTileRow.map { row ->
                    row.map { (it + tileNumber - 1) % 9 + 1 }
                }
            }

            return Chiton(Matrix.fromRows(rows = tileRows, defaultValue = 0))
        }

        private fun computeTileRow(tileCount: Int, inputLine: String): List<Int> =
            (0 until tileCount).fold(emptyList()) { row, tileNumber ->
                row + inputLine.map {
                    (it.digitToInt() + tileNumber - 1) % 9 + 1
                }
            }
    }
}