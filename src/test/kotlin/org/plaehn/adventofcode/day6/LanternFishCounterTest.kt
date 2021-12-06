package org.plaehn.adventofcode.day6

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class LanternFishCounterTest {

    @Test
    fun `Compute number of lantern fished after 80 days on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val lanternFishCounter = LanternFishCounter.fromInputString(input)
        val count = lanternFishCounter.computeNumberOfLanternFishesAfter(80)

        assertThat(count).isEqualTo(5934)
    }

    @Test
    fun `Compute number of lantern fished after 80 days on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val lanternFishCounter = LanternFishCounter.fromInputString(input)
        val count = lanternFishCounter.computeNumberOfLanternFishesAfter(80)

        assertThat(count).isEqualTo(377263)
    }
}


