package org.plaehn.adventofcode.day6

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp
import org.plaehn.adventofcode.day7.CrabBlast

internal class CrabBlastTest {

    @Test
    fun `Compute number of lantern fished after 80 days on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val crabBlast = CrabBlast.fromInputString(input)
        val position = crabBlast.findHorizontalPosition()

        assertThat(position).isEqualTo(2)
    }

}


