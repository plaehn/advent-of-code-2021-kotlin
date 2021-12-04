package org.plaehn.adventofcode.common

data class Matrix(private val matrix: Array<Array<Int>>) {

    operator fun get(rowNumber: Int): Array<Int> = matrix[rowNumber]

    fun replaceAll(transform: (Int) -> Int) {
        matrix.forEach { row ->
            row.forEachIndexed { index, value ->
                row[index] = transform(value)
            }
        }
    }

    fun values() = matrix.flatMap { it.asList() }

    fun rows() = matrix.toList()

    fun columns() = transpose().rows()

    private fun transpose(): Matrix {
        val numberOfRows = matrix.size
        val numberOfCols = matrix.first().size
        val transpose = Array(numberOfRows) { Array(numberOfCols) { -1 } }
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfCols) {
                transpose[j][i] = matrix[i][j]
            }
        }
        return Matrix(transpose)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }

    override fun toString() =
        matrix.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") {
                it.toString().padStart(4)
            }
        }

    companion object {
        fun fromRows(rows: List<List<Int>>) =
            Matrix(rows.map {
                it.toTypedArray()
            }.toTypedArray())
    }
}