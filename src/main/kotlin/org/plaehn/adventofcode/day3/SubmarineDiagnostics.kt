package org.plaehn.adventofcode.day3

class SubmarineDiagnostics(private val binaryNumbers: List<String>) {

    private val binaryNumberLength = binaryNumbers.first().length

    fun computePowerConsumption(): Int {
        var gamma = 0
        var epsilon = 0
        for (index in 0 until binaryNumberLength) {
            gamma = gamma shl 1
            epsilon = epsilon shl 1
            if (findMostCommonBitAt(index, binaryNumbers) == '1') {
                gamma = gamma or 1
            } else {
                epsilon = epsilon or 1
            }
        }
        return gamma * epsilon
    }

    fun computeLifeSupportRating() = computeOxygenGeneratorRating() * computeCo2ScrubberRating()

    private fun computeOxygenGeneratorRating() = reduceNumbers(::findMostCommonBitAt)

    private fun computeCo2ScrubberRating() = reduceNumbers(::findLeastCommonBitAt)

    private fun reduceNumbers(filterFor: (Int, List<String>) -> Char?): Int {
        var numbers = binaryNumbers
        var index = 0
        do {
            numbers = numbers.filter { it[index] == filterFor(index, numbers) }
            ++index
        } while (numbers.size > 1)
        return numbers.first().toInt(2)
    }

    private fun findMostCommonBitAt(index: Int, numbers: List<String>) =
        countCharsAt(index, numbers)
            .toSortedMap(Comparator.reverseOrder())
            .maxByOrNull { it.value }!!.key

    private fun findLeastCommonBitAt(index: Int, numbers: List<String>) =
        countCharsAt(index, numbers)
            .toSortedMap()
            .minByOrNull { it.value }!!.key

    private fun countCharsAt(index: Int, numbers: List<String>) =
        numbers
            .groupingBy { it[index] }
            .eachCount()
}