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
}