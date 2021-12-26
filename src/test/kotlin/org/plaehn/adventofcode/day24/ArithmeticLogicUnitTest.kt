package org.plaehn.adventofcode.day24

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ArithmeticLogicUnitTest {

    @Test
    fun `Binary example`() {
        val instructions = listOf(
            "inp w",
            "add z w",
            "mod z 2",
            "div w 2",
            "add y w",
            "mod y 2",
            "div w 2",
            "add x w",
            "mod x 2",
            "div w 2",
            "mod w 2"
        )
        val inputs = mutableListOf(15)

        val alu = ArithmeticLogicUnit.fromInputLines(instructions)

        val result = alu.execute(inputs)
        assertThat(result['x']).isEqualTo(1)
        assertThat(result['y']).isEqualTo(1)
        assertThat(result['z']).isEqualTo(1)
        assertThat(result['w']).isEqualTo(1)
    }

    @Test
    fun `Negation example`() {
        val instructions = listOf("inp x", "mul x -1")
        val inputs = mutableListOf(12)

        val alu = ArithmeticLogicUnit.fromInputLines(instructions)

        val result = alu.execute(inputs)
        assertThat(result['x']).isEqualTo(-12)
    }

    @Test
    fun `Compute largest model number for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val alu = ArithmeticLogicUnit.fromInputLines(inputLines)
        val largestModelNumber = alu.computeLargestAcceptedModelNumber()

        assertThat(largestModelNumber).isEqualTo(12345)
    }
}