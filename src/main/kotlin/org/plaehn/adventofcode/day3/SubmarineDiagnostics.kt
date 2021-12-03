package org.plaehn.adventofcode.day3

class SubmarineDiagnostics(private val binaryNumbers: List<String>) {

    private val binaryNumberLength = binaryNumbers.first().length

    fun computePowerConsumption(): Int {
        var gamma = 0
        var epsilon = 0
        for (index in 0 until binaryNumberLength) {
            gamma = gamma shl 1
            epsilon = epsilon shl 1
            val bitCount = countBitsAt(index, binaryNumbers)
            if (bitCount['1']!! > bitCount['0']!!) {
                gamma = gamma or 1
            } else {
                epsilon = epsilon or 1
            }
        }
        return gamma * epsilon
    }

    private fun countBitsAt(index: Int, numbers: List<String>) =
        numbers.groupingBy { it[index] }.eachCount()

    fun computeLifeSupportRating() =
        computeOxygenGeneratorRating() * computeCo2ScrubberRating()

    private fun computeOxygenGeneratorRating(): Int {
        var numbers = binaryNumbers
        var index = 0
        do {
            val bitCount = countBitsAt(index, numbers)
            val filterFor = if (bitCount['1']!! >= bitCount['0']!!) '1' else '0'
            numbers = numbers.filter { it[index] == filterFor }
            ++index
        } while (numbers.size > 1)
        return numbers.first().toInt(2)
    }

    private fun computeCo2ScrubberRating(): Int {
        var numbers = binaryNumbers
        var index = 0
        do {
            val bitCount = countBitsAt(index, numbers)
            val filterFor = if (bitCount['1']!! < bitCount['0']!!) '1' else '0'
            numbers = numbers.filter { it[index] == filterFor }
            ++index
        } while (numbers.size > 1)
        return numbers.first().toInt(2)
    }
}