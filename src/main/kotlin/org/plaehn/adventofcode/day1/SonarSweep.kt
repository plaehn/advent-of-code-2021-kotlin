package org.plaehn.adventofcode.day1

object SonarSweep {

    fun countIncreasingMeasurementsOfSlidingWindows(values: List<Int>) =
        countIncreasingMeasurements(values = sumSlidingWindows(values))

    private fun sumSlidingWindows(values: List<Int>) =
        values
            .windowed(size = 3)
            .map { it.sum() }

    fun countIncreasingMeasurements(values: List<Int>) =
        values
            .fold(Accumulator()) { accumulator: Accumulator, nextValue: Int ->
                accumulator.accumulate(nextValue)
            }.count

    private data class Accumulator(val count: Int = 0, val previous: Int = -1) {

        fun accumulate(nextValue: Int) =
            Accumulator(computeNewCount(nextValue), nextValue)

        private fun computeNewCount(nextValue: Int) =
            if (hasPrevious() && nextValue > previous)
                count + 1
            else
                count

        private fun hasPrevious() = previous >= 0
    }
}
