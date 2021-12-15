package org.plaehn.adventofcode.day15

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ChitonTest {

    @Test
    fun `Compute lowest total risk for small input`() {
        val inputLines = this::class.java.readLines("small_input.txt")

        val chiton = Chiton.fromInputLines(inputLines)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(7)
    }

    @Test
    fun `Compute lowest total risk for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val chiton = Chiton.fromInputLines(inputLines)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(40)
    }

    @Test
    fun `Compute lowest total risk for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val chiton = Chiton.fromInputLines(inputLines)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(707)
    }

    @Test
    fun `Compute lowest total risk with 5 tiles for small input`() {
        val inputLines = this::class.java.readLines("small_input.txt")

        val chiton = Chiton.fromInputLines(inputLines, tileCount = 5)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(100)
    }

    @Test
    fun `Compute lowest total risk with 5 tiles for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val chiton = Chiton.fromInputLines(inputLines, tileCount = 5)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(315)
    }

    @Test
    fun `Compute lowest total risk with 5 tiles for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val chiton = Chiton.fromInputLines(inputLines, tileCount = 5)

        val solution = chiton.computeLowestTotalRisk()

        assertThat(solution).isEqualTo(315)
    }
}
