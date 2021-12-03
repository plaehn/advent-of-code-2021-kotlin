package org.plaehn.adventofcode.day3

class SubmarineDiagnostics(private val binaryNumbers: List<String>) {

    fun computePowerConsumption(): Int {
        val len = binaryNumbers.first().length
        var gamma = 0
        var epsilon = 0
        for (i in 0 until len) {
            val zeroes = binaryNumbers.map { it[i] }.count { it == '0' }
            val ones = binaryNumbers.size - zeroes
            gamma = gamma shl 1
            epsilon = epsilon shl 1
            if (ones > zeroes) {
                gamma = gamma or 1
            } else {
                epsilon = epsilon or 1
            }
        }
        return gamma * epsilon
    }
}