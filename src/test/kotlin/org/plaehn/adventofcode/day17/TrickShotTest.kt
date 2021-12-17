package org.plaehn.adventofcode.day17

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class TrickShotTest {

    @Test
    fun `Compute highest vertical position for test input`() {
        val trickShot = TrickShot.fromInput("target area: x=20..30, y=-10..-5")

        val maxY = trickShot.computeHighestVerticalPositionOfAllSuccessfulShots()

        assertThat(maxY).isEqualTo(45)
    }

    @Test
    fun `Compute highest vertical position for puzzle input`() {
        val trickShot = TrickShot.fromInput("target area: x=14..50, y=-267..-225")

        val maxY = trickShot.computeHighestVerticalPositionOfAllSuccessfulShots()

        assertThat(maxY).isEqualTo(35511)
    }
}
