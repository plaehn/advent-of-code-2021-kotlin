package org.plaehn.adventofcode.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class PassagePathingTest {

    @Test
    fun `Count paths for small test input`() {
        val inputLines = this::class.java.readLines("small_test_input.txt")

        val passagePathing = PassagePathing.fromInputLines(inputLines)

        val pathCount = passagePathing.countPaths()

        assertThat(pathCount).isEqualTo(10)
    }

    @Test
    fun `Count paths for medium test input`() {
        val inputLines = this::class.java.readLines("medium_test_input.txt")

        val passagePathing = PassagePathing.fromInputLines(inputLines)

        val pathCount = passagePathing.countPaths()

        assertThat(pathCount).isEqualTo(19)
    }

    @Test
    fun `Count paths for large test input`() {
        val inputLines = this::class.java.readLines("large_test_input.txt")

        val passagePathing = PassagePathing.fromInputLines(inputLines)

        val pathCount = passagePathing.countPaths()

        assertThat(pathCount).isEqualTo(226)
    }

    @Test
    fun `Count paths for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val passagePathing = PassagePathing.fromInputLines(inputLines)

        val pathCount = passagePathing.countPaths()

        assertThat(pathCount).isEqualTo(5576)
    }
}