package org.plaehn.adventofcode.day22

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ReactorRebootTest {

    @Test
    fun `Compute number of cubes that are on after reboot for small test input`() {
        val inputLines = this::class.java.readLines("small_test_input.txt")

        val reactorReboot = ReactorReboot.fromInputLines(inputLines, -50..50)
        val count = reactorReboot.computeNumberOfCubesThatAreOnAfterReboot()

        assertThat(count).isEqualTo(39)
    }

    @Test
    fun `Compute number of cubes that are on after reboot for test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val reactorReboot = ReactorReboot.fromInputLines(inputLines, -50..50)
        val count = reactorReboot.computeNumberOfCubesThatAreOnAfterReboot()

        assertThat(count).isEqualTo(590784)
    }

    @Test
    fun `Compute number of cubes that are on after reboot for puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val reactorReboot = ReactorReboot.fromInputLines(inputLines, -50..50)
        val count = reactorReboot.computeNumberOfCubesThatAreOnAfterReboot()
        assertThat(count).isEqualTo(587097)
    }
}