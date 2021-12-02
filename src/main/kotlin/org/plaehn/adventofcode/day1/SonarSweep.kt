package org.plaehn.adventofcode.day1

class SonarSweep(private val measurements: List<Int>) {

    fun countIncreasingMeasurements() =
        measurements
            .fold(Accumulator()) { accumulator: Accumulator, measurement: Int ->
                accumulator.accumulateNext(measurement)
            }.count

    private data class Accumulator(val count: Int = 0, val previous: Int = -1) {

        fun accumulateNext(measurement: Int) =
            Accumulator(computeNewCount(measurement), measurement)

        private fun computeNewCount(measurement: Int) =
            if (hasPrevious() && measurement > previous)
                count + 1
            else
                count

        private fun hasPrevious() = previous >= 0
    }
}
