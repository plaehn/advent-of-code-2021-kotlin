package org.plaehn.adventofcode.day23

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class AmphipodTest {

    @Test
    fun `Compute number of cubes that are on after reboot for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val amphipod = Amphipod.fromInputLines(inputLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(12521)
    }

    @Test
    fun `Compute number of cubes that are on after reboot for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val amphipod = Amphipod.fromInputLines(inputLines)
        val energy = amphipod.computeLeastEnergyToOrganizeAmphipods()

        assertThat(energy).isEqualTo(12521)
    }

}