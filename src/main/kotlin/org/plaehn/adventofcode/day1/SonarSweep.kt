package org.plaehn.adventofcode.day1

class SonarSweep(val measurements: List<Int>) {

    fun countIncreasingMeasurements(): Int {
        val acc = measurements.fold(Pair(0, -1)) { acc: Pair<Int, Int>, measurement: Int ->
            if (acc.second > 0 && measurement > acc.second) {
                Pair(acc.first + 1, measurement)
            } else {
                Pair(acc.first, measurement)
            }
        }
        return acc.first
    }
}
