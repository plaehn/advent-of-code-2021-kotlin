package org.plaehn.adventofcode.day20

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.groupByBlankLines

class TrenchMap(private val matrix: Matrix<Boolean>, private val algorithm: String) {

    private var infinitePixelsAreLit = false

    fun computeNumberOfLitPixelsAfterEnhancement() =
        (1..numberOfSteps)
            .fold(matrix) { matrix, _ -> enhanceImage(matrix) }
            .toMap()
            .count { it.value }

    private fun enhanceImage(matrix: Matrix<Boolean>) =
        Matrix.fromDimensions(matrix.width(), matrix.height(), false)
            .apply {
                matrix.rows().forEachIndexed { x, row ->
                    row.forEachIndexed { y, _ ->
                        val index = matrix.computeIndex(x, y)
                        this[y][x] = algorithm[index].isLit()
                    }
                }
                infinitePixelsAreLit = if (infinitePixelsAreLit) algorithm[511].isLit() else algorithm[0].isLit()
            }

    private fun Matrix<Boolean>.computeIndex(x: Int, y: Int) =
        sequence {
            (-1..1).forEach { dy ->
                (-1..1).forEach { dx ->
                    val coord = Coord(x + dx, y + dy)
                    val isLit = if (this@computeIndex.isInsideBounds(coord)) {
                        this@computeIndex[coord]
                    } else {
                        infinitePixelsAreLit
                    }
                    if (isLit) {
                        yield('1')
                    } else {
                        yield('0')
                    }
                }
            }
        }.joinToString("").toInt(2)

    companion object {

        private const val numberOfSteps = 2

        fun fromInput(input: String): TrenchMap {
            val (algorithm, image) = input.groupByBlankLines()
            val lines = image.lines()
            val height = lines.size + 2 * numberOfSteps
            val width = lines.first().length + 2 * numberOfSteps
            val matrix = Matrix.fromDimensions(width, height, false)
            lines.forEachIndexed { x, line ->
                line.forEachIndexed { y, ch ->
                    matrix[x + numberOfSteps][y + numberOfSteps] = ch.isLit()
                }
            }
            return TrenchMap(matrix, algorithm)
        }
    }
}

private fun Char.isLit() = this == '#'
