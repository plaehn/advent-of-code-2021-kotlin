package org.plaehn.adventofcode.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

@ExperimentalStdlibApi
internal class ExtendedPolymerizationTest {

    @Test
    fun `Compute solution after 10 steps for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution(10)

        assertThat(solution).isEqualTo(1588)
    }

    @Test
    fun `Compute solution after 10 steps for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution(10)

        assertThat(solution).isEqualTo(3406)
    }

    @Test
    fun `Compute solution after 40 steps for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution(40)

        assertThat(solution).isEqualTo(2188189693529)
    }

    @Test
    fun `Compute solution after 40 steps for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val polymerization = ExtendedPolymerization.fromInput(input)

        val solution = polymerization.computeSolution(40)

        assertThat(solution).isEqualTo(3941782230241)
    }
}
