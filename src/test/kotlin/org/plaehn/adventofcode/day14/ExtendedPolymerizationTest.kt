package org.plaehn.adventofcode.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class ExtendedPolymerizationTest {

    @Test
    fun `Compute solution for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution()

        assertThat(solution).isEqualTo(1588)
    }

    @Test
    fun `Compute solution for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution()

        assertThat(solution).isEqualTo(3406)
    }
}