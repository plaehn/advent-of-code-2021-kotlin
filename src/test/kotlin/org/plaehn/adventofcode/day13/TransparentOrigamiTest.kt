package org.plaehn.adventofcode.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class TransparentOrigamiTest {

    @Test
    fun `Compute number of dots after first fold for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val dotCount = transparentOrigami.computeNumberOfDotsAfterFirstFold()

        assertThat(dotCount).isEqualTo(17)
    }

    @Test
    fun `Compute number of dots after first fold for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val dotCount = transparentOrigami.computeNumberOfDotsAfterFirstFold()

        assertThat(dotCount).isEqualTo(759)
    }
}
