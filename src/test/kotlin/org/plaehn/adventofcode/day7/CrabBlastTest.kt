package org.plaehn.adventofcode.day7

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class CrabBlastTest {

    @Test
    fun `Compute position on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val crabBlast = CrabBlast.fromInputString(input)
        val position = crabBlast.findHorizontalPosition()

        assertThat(position).isEqualTo(2)
    }

    @Test
    fun `Compute position on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val crabBlast = CrabBlast.fromInputString(input)
        val position = crabBlast.findHorizontalPosition()

        // XXX this is wrong
        assertThat(position).isEqualTo(331)
    }
}


