package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.groupByBlankLines

class TransparentOrigami(private val dots: Set<Coord>, private val folds: List<Fold>) {

    fun computeCode() = folds.fold(dots, ::foldPaper).toCode()

    private fun Set<Coord>.toCode() =
        (0 until height()).joinToString("\n") { y ->
            (0 until width()).map { x ->
                if (contains(Coord(x, y))) 'X' else '.'
            }.joinToString("")
        }

    private fun Set<Coord>.height() = 1 + maxOf { it.y }

    private fun Set<Coord>.width() = 1 + maxOf { it.x }

    fun computeNumberOfDotsAfterFirstFold() = foldPaper(dots, folds.first()).size

    private fun foldPaper(dots: Set<Coord>, fold: Fold) =
        dots.map { dot ->
            if (fold.axis == 'y') {
                dot.copy(y = fold.mapIndex(dot.y))
            } else {
                dot.copy(x = fold.mapIndex(dot.x))
            }
        }.toSet()

    private fun Fold.mapIndex(index: Int) =
        if (index <= line) index else 2 * line - index


    companion object {
        fun fromInput(input: String): TransparentOrigami {
            val (dotsStr, foldInstructionsStr) = input.groupByBlankLines()
            val dots = dotsStr.lines().map { Coord.fromString(it) }.toSet()
            val folds = foldInstructionsStr.lines().map { Fold.fromString(it) }
            return TransparentOrigami(dots, folds)
        }
    }
}

data class Fold(val axis: Char, val line: Int) {
    companion object {
        fun fromString(input: String): Fold {
            val (dimension, value) = input.split(' ').last().split('=')
            return Fold(dimension.first(), value.toInt())
        }
    }
}
