package org.plaehn.adventofcode.day3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SubmarineDiagnosticsTest {

    @Test
    fun `Compute power consumption on test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val diagnostics = SubmarineDiagnostics(lines)

        val powerConsumption = diagnostics.computePowerConsumption()

        assertThat(powerConsumption).isEqualTo(198)
    }

    @Test
    fun `Compute power consumption on puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val diagnostics = SubmarineDiagnostics(lines)

        val powerConsumption = diagnostics.computePowerConsumption()

        assertThat(powerConsumption).isEqualTo(2648450)
    }
}


