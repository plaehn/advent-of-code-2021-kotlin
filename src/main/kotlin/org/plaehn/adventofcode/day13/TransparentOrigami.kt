package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.groupByBlankLines

class TransparentOrigami(private val dots: Set<Coord>, private val foldInstructions: List<FoldInstruction>) {

    fun computeCode(): String {
        return ""
    }

    fun computeNumberOfDotsAfterFirstFold() = fold(dots, foldInstructions.first()).size

    private fun fold(dots: Set<Coord>, foldInstruction: FoldInstruction): Set<Coord> {
        return dots.map { dot ->
            if (foldInstruction.dimension == 'y') {
                Coord(dot.x, if (dot.y > foldInstruction.value) 2 * foldInstruction.value - dot.y else dot.y)
            } else {
                Coord(if (dot.x > foldInstruction.value) 2 * foldInstruction.value - dot.x else dot.x, dot.y)
            }
        }.toSet()
    }


    companion object {
        fun fromInput(input: String): TransparentOrigami {
            val (dotsStr, foldInstructionsStr) = input.groupByBlankLines()
            val dots = dotsStr.lines().map { Coord.fromString(it) }.toSet()
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
