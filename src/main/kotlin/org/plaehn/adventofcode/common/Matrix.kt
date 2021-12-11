package org.plaehn.adventofcode.common

data class Matrix<T>(
    private val matrix: List<MutableList<T>>,
    private val defaultValue: T
) {

    operator fun get(rowNumber: Int): MutableList<T> = matrix[rowNumber]

    fun width() = matrix.first().size

    fun height() = matrix.size

    fun replaceAll(transform: (T) -> T) {
        matrix.forEach { row ->
            row.forEachIndexed { index, value ->
                row[index] = transform(value)
            }
        }
    }

    fun values() = matrix.flatten()

    fun rows(): List<MutableList<T>> = matrix.toList()

    fun columns() = transpose().rows()

    fun toMap(): Map<Coord, T> =
        sequence {
            for (y in 0 until height()) {
                for (x in 0 until width()) {
                    yield(Coord(x, y) to matrix[y][x])
                }
            }
        }.toMap()

    fun neighbors(coord: Coord) =
        neighborOffsets()
            .map { coord + it }
            .filter { isInsideBounds(it) }

    private fun neighborOffsets() = listOf(Coord(-1, 0), Coord(0, 1), Coord(1, 0), Coord(0, -1))

    private fun isInsideBounds(coord: Coord) = coord.y in 0 until height() && coord.x in 0 until width()

    private fun transpose(): Matrix<T> {
        val transpose = MutableList(width()) { MutableList(height()) { defaultValue } }
        for (i in 0 until height()) {
            for (j in 0 until width()) {
                transpose[j][i] = matrix[i][j]
            }
        }
        return Matrix(transpose, defaultValue)
    }

    override fun toString() =
        matrix.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") {
                it.toString().padStart(4)
            }
        }

    companion object {

        fun <T> fromDimensions(width: Int, height: Int, defaultValue: T) =
            Matrix(MutableList(height) { MutableList(width) { defaultValue } }, defaultValue)

        fun <T> fromRows(rows: List<List<T>>, defaultValue: T) =
            Matrix(rows.map {
                it.toMutableList()
            }, defaultValue)
    }
}

data class Coord(val x: Int, val y: Int) {
    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y)

    companion object {}
}


