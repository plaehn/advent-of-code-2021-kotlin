package org.plaehn.adventofcode.day11

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class DumboOctopusGridTest {

    @Test
    fun `Compute number of flashes on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val scorer = DumboOctopusGrid.fromInputLines(inputLines)

        val flashes = scorer.computeNumberOfFlashes()

        assertThat(flashes).isEqualTo(1656)
    }

    @Test
    fun `Compute number of flashes on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val scorer = DumboOctopusGrid.fromInputLines(inputLines)

        val flashes = scorer.computeNumberOfFlashes()

        assertThat(flashes).isEqualTo(1601)
    }
}