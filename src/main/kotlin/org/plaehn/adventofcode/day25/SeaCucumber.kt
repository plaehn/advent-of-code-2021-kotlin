package org.plaehn.adventofcode.day25

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix

class SeaCucumber(private val matrix: Matrix<Char>) {

    fun computeNumberOfStepsUntilNoMovement(): Int {
        var steps = 0
        var current = matrix
        do {
            ++steps
            val next = performStep(current)
            if (current == next) return steps
            current = next
        } while (true)
    }

    private fun performStep(current: Matrix<Char>) =
        Matrix.fromMap(current.toMap().mapNotNull { (coord, ch) ->
            when (ch) {
                '>' -> current.stepRightIfPossible(coord, ch)
                'v' -> current.stepDownIfPossible(coord, ch)
                else -> null
            }
        }.toMap(), current.width(), current.height(), '.')


    private fun Matrix<Char>.stepRightIfPossible(coord: Coord, ch: Char): Pair<Coord, Char> {
        val newPlace = coord.stepRight()
        return if (this[newPlace] == '.') {
            newPlace to '>'
        } else {
            coord to ch
        }
    }

    private fun Matrix<Char>.stepDownIfPossible(coord: Coord, ch: Char): Pair<Coord, Char> {
        val newPlace = coord.stepDown()
        return if ((this[newPlace] == '.' && this[newPlace.stepLeft()] != '>')
            || (this[newPlace] == '>' && this[newPlace.stepRight()] == '.')
        ) {
            newPlace to 'v'
        } else {
            coord to ch
        }
    }

    private fun Coord.stepLeft() = Coord((if (x == 0) matrix.width() else x) - 1, y)

    private fun Coord.stepRight() = Coord((x + 1) % matrix.width(), y)

    private fun Coord.stepDown() = Coord(x, (y + 1) % matrix.height())

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            SeaCucumber(Matrix.fromRows(inputLines.map { it.toList() }, '.'))
    }
}


