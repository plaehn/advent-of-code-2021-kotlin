package org.plaehn.adventofcode.day24

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ArithmeticLogicUnitTest {

    @Test
    fun `Compute largest model number for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val alu = ArithmeticLogicUnit(inputLines)
        val largestModelNumber = alu.solvePart1()

        assertThat(largestModelNumber).isEqualTo(29599469991739)
    }

    @Test
    fun `Compute smallest model number for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val alu = ArithmeticLogicUnit(inputLines)
        val largestModelNumber = alu.solvePart2()

        assertThat(largestModelNumber).isEqualTo(17153114691118)
    }
}