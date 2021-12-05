package org.plaehn.adventofcode.day5

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

    fun computeNumberOfPointsWithAtLeastTwoOverlappingLines(): Int {
        lines
            .filter { it.isHorizontal() || it.isVertical() }
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
            matrix[pos.y][pos.x] += 1
            pos += step
        } while (pos != line.end)
        matrix[pos.y][pos.x] += 1
    }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            HydrothermalVenture(inputLines.map { Line.fromString(it) })
    }
}

data class Coord(val x: Int, val y: Int) {

    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y)

    companion object {
        fun fromString(input: String): Coord {
            val (x, y) = input.split(",")
            return Coord(x.toInt(), y.toInt())
        }
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
