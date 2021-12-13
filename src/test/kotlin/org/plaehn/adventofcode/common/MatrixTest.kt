package org.plaehn.adventofcode.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class MatrixTest {

    private val evenMatrix = Matrix.fromRows(
        listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 15, 16)
        ), 0
    )

    private val oddMatrix = Matrix.fromRows(
        listOf(
            listOf(1, 2, 3, 4, 5),
            listOf(6, 7, 8, 9, 10),
            listOf(11, 12, 13, 14, 15),
            listOf(16, 17, 18, 19, 20),
            listOf(21, 22, 23, 24, 25),
        ), 0
    )

    @Test
    fun reflectHorizontallyEvenMatrix() {
        val expectedMatrix = Matrix.fromRows(
            listOf(
                listOf(13, 14, 15, 16),
                listOf(9, 10, 11, 12),
                listOf(5, 6, 7, 8),
                listOf(1, 2, 3, 4)
            ), 0
        )
        assertThat(evenMatrix.reflectHorizontally()).isEqualTo(expectedMatrix)
    }

    @Test
    fun reflectHorizontallyOddMatrix() {
        val expectedMatrix = Matrix.fromRows(
            listOf(
                listOf(21, 22, 23, 24, 25),
                listOf(16, 17, 18, 19, 20),
                listOf(11, 12, 13, 14, 15),
                listOf(6, 7, 8, 9, 10),
                listOf(1, 2, 3, 4, 5)
            ), 0
        )
        assertThat(oddMatrix.reflectHorizontally()).isEqualTo(expectedMatrix)
    }

    @Test
    fun reflectVerticallyEven() {
        val expectedMatrix = Matrix.fromRows(
            listOf(
                listOf(4, 3, 2, 1),
                listOf(8, 7, 6, 5),
                listOf(12, 11, 10, 9),
                listOf(16, 15, 14, 13)
            ), 0
        )
        assertThat(evenMatrix.reflectVertically()).isEqualTo(expectedMatrix)
    }

    @Test
    fun reflectVerticallyOdd() {
        val expectedMatrix = Matrix.fromRows(
            listOf(
                listOf(5, 4, 3, 2, 1),
                listOf(10, 9, 8, 7, 6),
                listOf(15, 14, 13, 12, 11),
                listOf(20, 19, 18, 17, 16),
                listOf(25, 24, 23, 22, 21)
            ), 0
        )
        assertThat(oddMatrix.reflectVertically()).isEqualTo(expectedMatrix)
    }

    @Test
    fun rotateOdd() {
        assertThat(oddMatrix.rotateLeft().rotateRight()).isEqualTo(oddMatrix)
    }

    @Test
    fun rotateEven() {
        assertThat(evenMatrix.rotateLeft().rotateRight()).isEqualTo(evenMatrix)
    }
}
