package org.plaehn.adventofcode.day23

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class AmphipodTest {

    @Test
    fun `Solve part 1 for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val amphipod = Amphipod.fromInputLines(inputLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(12521)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val amphipod = Amphipod.fromInputLines(inputLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(18051)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val expandedLines = inputLines.take(3) + listOf("  #D#C#B#A#  ", "  #D#B#A#C#  ") + inputLines.drop(3)
        val amphipod = Amphipod.fromInputLines(expandedLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(44169)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val expandedLines = inputLines.take(3) + listOf("  #D#C#B#A#  ", "  #D#B#A#C#  ") + inputLines.drop(3)
        val amphipod = Amphipod.fromInputLines(expandedLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(50245)
    }
}