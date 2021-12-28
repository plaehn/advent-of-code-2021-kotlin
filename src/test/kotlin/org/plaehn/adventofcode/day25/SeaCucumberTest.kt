package org.plaehn.adventofcode.day25

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SeaCucumberTest {

    @Test
    fun `Compute number of steps for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val seaCucumber = SeaCucumber.fromInputLines(inputLines)
        val steps = seaCucumber.computeNumberOfStepsUntilNoMovement()

        assertThat(steps).isEqualTo(58)
    }

    @Test
    fun `Compute number of steps for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val seaCucumber = SeaCucumber.fromInputLines(inputLines)
        val steps = seaCucumber.computeNumberOfStepsUntilNoMovement()

        assertThat(steps).isEqualTo(453)
    }
}