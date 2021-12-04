package org.plaehn.adventofcode.common

data class Matrix<T>(
    private val matrix: List<MutableList<T>>,
    private val defaultValue: T
) {

    operator fun get(rowNumber: Int): List<T> = matrix[rowNumber]

    fun replaceAll(transform: (T) -> T) {
        matrix.forEach { row ->
            row.forEachIndexed { index, value ->
                row[index] = transform(value)
            }
        }
    }

    fun values() = matrix.flatten()

    fun rows() = matrix.toList()

    fun columns() = transpose().rows()

    private fun transpose(): Matrix<T> {
        val numberOfRows = matrix.size
        val numberOfCols = matrix.first().size
        val transpose = MutableList(numberOfCols) { MutableList(numberOfRows) { defaultValue } }
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfCols) {
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
        fun <T> fromRows(rows: List<List<T>>, defaultValue: T) =
            Matrix(rows.map {
                it.toMutableList()
            }, defaultValue)
    }
}