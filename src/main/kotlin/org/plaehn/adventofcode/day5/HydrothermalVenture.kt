package org.plaehn.adventofcode.day5

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.Matrix
import kotlin.math.sign

class HydrothermalVenture(
    private val lines: List<Line>
) {

    private val matrix: Matrix<Int>

    init {
        val allCoords = lines.map { it.start } + lines.map { it.end }
        val width = 1 + allCoords.maxOf { it.x }
        val height = 1 + allCoords.maxOf { it.y }
        matrix = Matrix.fromDimensions(width, height, 0)
    }

    fun computeNumberOfPointsWithAtLeastTwoOverlappingLines(includeDiagonals: Boolean): Int {
        lines
            .filter { includeDiagonals || it.isHorizontal() || it.isVertical() }
            .forEach { line ->
                drawIn(line)
            }
        return matrix.values().count { it >= 2 }
    }

    private fun drawIn(line: Line) {
        val step = Coord(
            x = (line.end.x - line.start.x).sign,
            y = (line.end.y - line.start.y).sign
        )

        var pos = line.start
        do {
            matrix[pos] += 1
            pos += step
        } while (pos != line.end)
        matrix[pos] += 1
    }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            HydrothermalVenture(inputLines.map { Line.fromString(it) })
    }
}

data class Line(val start: Coord, val end: Coord) {

    fun isHorizontal() = start.y == end.y

    fun isVertical() = start.x == end.x

    companion object {
        fun fromString(input: String): Line {
            val (start, end) = input.split(" -> ")
            return Line(Coord.fromString(start), Coord.fromString(end))
        }
    }
}
