package org.plaehn.adventofcode.day20

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class TrenchMapTest {

    @Test
    fun `Compute number of lit pixels after 2 steps for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val trenchMap = TrenchMap.fromInput(input, 2)

        assertThat(trenchMap.computeNumberOfLitPixelsAfterEnhancement()).isEqualTo(35)
    }

    @Test
    fun `Compute number of lit pixels after 2 steps for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val trenchMap = TrenchMap.fromInput(input, 2)

        assertThat(trenchMap.computeNumberOfLitPixelsAfterEnhancement()).isEqualTo(5361)
    }

    @Test
    fun `Compute number of lit pixels after 50 steps for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val trenchMap = TrenchMap.fromInput(input, 50)

        assertThat(trenchMap.computeNumberOfLitPixelsAfterEnhancement()).isEqualTo(3351)
    }

    @Test
    fun `Compute number of lit pixels after 50 steps for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val trenchMap = TrenchMap.fromInput(input, 50)

        assertThat(trenchMap.computeNumberOfLitPixelsAfterEnhancement()).isEqualTo(16826)
    }
}