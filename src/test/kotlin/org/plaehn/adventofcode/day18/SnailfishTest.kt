package org.plaehn.adventofcode.day18

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SnailfishTest {

    @Test
    fun `Compute magnitude of final sum for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val snailfish = Snailfish.fromInputLines(inputLines)

        assertThat(snailfish.computeMagnitudeOfFinalSum()).isEqualTo(4140)
    }

    @Test
    fun `Compute magnitude of final sum for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val snailfish = Snailfish.fromInputLines(inputLines)

        assertThat(snailfish.computeMagnitudeOfFinalSum()).isEqualTo(4111)
    }

    @Test
    fun `Compute max magnitude of all pairs for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val snailfish = Snailfish.fromInputLines(inputLines)

        assertThat(snailfish.computeMaxMagnitude()).isEqualTo(3993)
    }

    @Test
    fun `Compute max magnitude of all pairs for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val snailfish = Snailfish.fromInputLines(inputLines)

        assertThat(snailfish.computeMaxMagnitude()).isEqualTo(4917)
    }
}