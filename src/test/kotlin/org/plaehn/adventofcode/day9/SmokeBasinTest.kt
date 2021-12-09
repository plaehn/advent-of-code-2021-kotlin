package org.plaehn.adventofcode.day9

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SmokeBasinTest {

    @Test
    fun `Compute sum of risk levels on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val smokeBasin = SmokeBasin.fromInputLines(inputLines)

        val sumOfRiskLevels = smokeBasin.computeSumOfRiskLevels()

        assertThat(sumOfRiskLevels).isEqualTo(15)
    }

    @Test
    fun `Compute sum of risk levels on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val smokeBasin = SmokeBasin.fromInputLines(inputLines)

        val sumOfRiskLevels = smokeBasin.computeSumOfRiskLevels()

        assertThat(sumOfRiskLevels).isEqualTo(532)
    }
}


