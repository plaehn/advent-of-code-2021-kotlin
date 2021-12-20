package org.plaehn.adventofcode.day19

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class BeaconScannerTest {

    @Test
    fun `Compute number of beacons for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val beaconScanner = BeaconScanner.fromInput(input)

        assertThat(beaconScanner.computeNumberOfBeacons()).isEqualTo(79)
    }

    @Test
    fun `Compute number of beacons for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val beaconScanner = BeaconScanner.fromInput(input)

        assertThat(beaconScanner.computeNumberOfBeacons()).isEqualTo(392)
    }
}