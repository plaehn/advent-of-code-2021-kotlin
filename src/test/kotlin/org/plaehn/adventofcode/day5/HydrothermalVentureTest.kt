package org.plaehn.adventofcode.day5

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class HydrothermalVentureTest {

    @Test
    fun `Compute number of points with at least two overlapping lines on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val hydrothermalVenture = HydrothermalVenture.fromInputLines(inputLines)
        val count = hydrothermalVenture.computeNumberOfPointsWithAtLeastTwoOverlappingLines()

        assertThat(count).isEqualTo(5)
    }

    @Test
    fun `Compute number of points with at least two overlapping lines on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val hydrothermalVenture = HydrothermalVenture.fromInputLines(inputLines)
        val count = hydrothermalVenture.computeNumberOfPointsWithAtLeastTwoOverlappingLines()

        assertThat(count).isEqualTo(8060)
    }
}


