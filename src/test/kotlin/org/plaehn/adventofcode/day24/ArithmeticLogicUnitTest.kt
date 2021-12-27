package org.plaehn.adventofcode.day24

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ArithmeticLogicUnitTest {

    @Test
    fun `Compute largest model number for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val alu = ArithmeticLogicUnit.fromInputLines(inputLines)
        val largestModelNumber = alu.computeLargestAcceptedModelNumber()

        assertThat(largestModelNumber).isEqualTo(12345)
    }
}