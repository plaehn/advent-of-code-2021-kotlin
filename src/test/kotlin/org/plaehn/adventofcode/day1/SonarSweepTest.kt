package org.plaehn.adventofcode.day1

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SonarSweepTest {

    @Test
    fun `Count measurements larger than previous on test input`() {
        val measurements = readInput("test_input.txt")

        val numberOfIncreasingMeasurements = SonarSweep.countIncreasingMeasurements(measurements)

        assertThat(numberOfIncreasingMeasurements).isEqualTo(7)
    }

    @Test
    fun `Count measurements larger than previous on puzzle input`() {
        val measurements = readInput("puzzle_input.txt")

        val numberOfIncreasingMeasurements = SonarSweep.countIncreasingMeasurements(measurements)

        assertThat(numberOfIncreasingMeasurements).isEqualTo(1448)
    }

    @Test
    fun `Count measurements larger than previous using windows of size 3 on test input`() {
        val measurements = readInput("test_input.txt")

        val numberOfIncreasingMeasurements = SonarSweep.countIncreasingMeasurements(
            measurements,
            slidingWindowSize = 3
        )

        assertThat(numberOfIncreasingMeasurements).isEqualTo(5)
    }

    @Test
    fun `Count measurements larger than previous using windows of size 3 on puzzle input`() {
        val measurements = readInput("puzzle_input.txt")

        val numberOfIncreasingMeasurements = SonarSweep.countIncreasingMeasurements(
            measurements,
            slidingWindowSize = 3
        )
        
        assertThat(numberOfIncreasingMeasurements).isEqualTo(1471)
    }

    private fun readInput(ressourceName: String): List<Int> =
        this::class.java
            .getResource(ressourceName)!!
            .readText()
            .lines()
            .filter { it.isNotBlank() }
            .map { Integer.valueOf(it) }
}
