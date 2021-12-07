package org.plaehn.adventofcode.day7

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class CrabBlastTest {

    @Test
    fun `Compute minimal fuel consumption on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val crabBlast = CrabBlast.fromInputString(input)
        val minimalFuelConsumption = crabBlast.findMinimalFuelConsumption()

        assertThat(minimalFuelConsumption).isEqualTo(37)
    }

    @Test
    fun `Compute minimal fuel consumption on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val crabBlast = CrabBlast.fromInputString(input)
        val minimalFuelConsumption = crabBlast.findMinimalFuelConsumption()

        assertThat(minimalFuelConsumption).isEqualTo(349769)
    }
}


