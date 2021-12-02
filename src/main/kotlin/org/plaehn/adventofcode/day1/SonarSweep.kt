package org.plaehn.adventofcode.day1

object SonarSweep {

    fun countIncreasingMeasurements(values: List<Int>, slidingWindowSize: Int = 1) =
        countIncreasing(values = sumSlidingWindows(slidingWindowSize, values))

    private fun sumSlidingWindows(size: Int, values: List<Int>) =
        values
            .windowed(size)
            .map { it.sum() }

    private fun countIncreasing(values: List<Int>) =
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
