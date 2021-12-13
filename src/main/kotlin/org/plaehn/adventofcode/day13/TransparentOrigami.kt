package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.groupByBlankLines

class TransparentOrigami(dots: List<Coord>, private val foldInstructions: List<FoldInstruction>) {

    private var paper: Matrix<Boolean>

    init {
        val width = 1 + dots.maxOf { it.x }
        val height = 1 + dots.maxOf { it.y }
        paper = Matrix.fromDimensions(width, height, false)
        dots.forEach { dot ->
            paper[dot.y][dot.x] = true
        }
    }

    fun computeNumberOfDotsAfterFirstFold(): Int {
        val firstFold = foldInstructions.first()
        paper = if (firstFold.dimension == 'y') {
            paper.foldUp(firstFold.value)
        } else {
            paper.foldLeft(firstFold.value)
        }
        return paper.values().count { it }
    }

    private fun Matrix<Boolean>.foldUp(value: Int): Matrix<Boolean> {
        check(height() / 2 == value) { "Fold is not in the middle; height is ${height()}, fold is $value" }
        (0 until value).forEach { y ->
            val topRow = this[y]
            val bottomIdx = height() - y - 1
            val bottomRow = this[bottomIdx]
            topRow.zip(bottomRow).forEachIndexed { x, (topElement, bottomElement) ->
                val newElement = topElement || bottomElement
                this[y][x] = newElement
            }
        }
        return this.restrictToRows(0, value)
    }

    private fun Matrix<Boolean>.foldLeft(value: Int): Matrix<Boolean> {
        check(width() / 2 == value) { "Fold is not in the middle; width is ${width()}, fold is $value" }
        return transpose().foldUp(value).transpose()
    }

    companion object {
        fun fromInput(input: String): TransparentOrigami {
            val (dotsStr, foldInstructionsStr) = input.groupByBlankLines()
            val dots = dotsStr.lines().map { Coord.fromString(it) }
            val foldInstructions = foldInstructionsStr.lines().map { FoldInstruction.fromString(it) }
            return TransparentOrigami(dots, foldInstructions)
        }
    }
}

data class FoldInstruction(val dimension: Char, val value: Int) {
    companion object {
        fun fromString(input: String): FoldInstruction {
            val (dimension, value) = input.split(' ').last().split('=')
            return FoldInstruction(dimension.first(), value.toInt())
        }
    }
}
