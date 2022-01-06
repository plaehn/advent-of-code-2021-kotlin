package org.plaehn.adventofcode.day21

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class DiracDiceTest {

    @Test
    fun `Solve part 1 for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val diracDice = DiracDice.fromInputLines(inputLines)

        assertThat(diracDice.solvePart1()).isEqualTo(739785)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val diracDice = DiracDice.fromInputLines(inputLines)

        assertThat(diracDice.solvePart1()).isEqualTo(518418)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val diracDice = DiracDice.fromInputLines(inputLines)

        assertThat(diracDice.solvePart2()).isEqualTo(444356092776315)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val diracDice = DiracDice.fromInputLines(inputLines)

        assertThat(diracDice.solvePart2()).isEqualTo(116741133558209)
    }
}