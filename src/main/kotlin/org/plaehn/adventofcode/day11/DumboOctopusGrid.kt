package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix

class DumboOctopusGrid(
    private val matrix: Matrix<Int>
) {

    fun computeNumberOfFlashes() =
        (1..100).fold(0) { flashCount, _ -> flashCount + flashesInStep() }

    private fun flashesInStep(): Int {
        matrix.replaceAll { it + 1 }

        val flashers = mutableSetOf<Coord>()
        do {
            val newFlashers = findNewFlashers(flashers)
            newFlashers.forEach { coord ->
                flash(coord)
                flashers.add(coord)
            }
        } while (newFlashers.isNotEmpty())

        flashers.forEach { coord -> matrix[coord.y][coord.x] = 0 }

        return flashers.size
    }

    private fun findNewFlashers(flashers: Set<Coord>) =
        matrix
            .toMap()
            .filter { (coord, value) ->
                value > 9 && !flashers.contains(coord)
            }
            .keys

    private fun flash(coord: Coord) {
        matrix
            .neighbors(coord, includeDiagonals = true)
            .forEach { neighbor ->
                matrix[neighbor.y][neighbor.x] += 1
            }
    }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            DumboOctopusGrid(
                Matrix.fromRows(
                    rows = inputLines.map { inputLine -> inputLine.map { it.digitToInt() } },
                    defaultValue = 0
                )
            )
    }
}



