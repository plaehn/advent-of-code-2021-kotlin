package org.plaehn.adventofcode.day8

class SevenSegmentSearch(
    private val entries: List<Entry>
) {

    private val digit2Segment = mapOf(
        0 to BagOfChars.fromString("abcefg"),
        1 to BagOfChars.fromString("cf"),
        2 to BagOfChars.fromString("acdeg"),
        3 to BagOfChars.fromString("acdfg"),
        4 to BagOfChars.fromString("bcdf"),
        5 to BagOfChars.fromString("abdfg"),
        6 to BagOfChars.fromString("abdefg"),
        7 to BagOfChars.fromString("acf"),
        8 to BagOfChars.fromString("abcdefg"),
        9 to BagOfChars.fromString("abcdfg")
    )

    private val easyDigitSizes = digit2Segment.values.findUniqueSizes()

    fun countEasyDigits() =
        entries
            .map { it.fourDigitOutputValues }
            .flatten()
            .count { easyDigitSizes.contains(it.size) }

    private fun Collection<BagOfChars>.findUniqueSizes() =
        groupingBy { it.size }
            .eachCount()
            .filter { it.value == 1 }
            .map { it.key }
            .toSet()

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            SevenSegmentSearch(inputLines.map { Entry.fromInputLine(it) })
    }
}

data class Entry(
    val uniqueSignalPatterns: List<BagOfChars>,
    val fourDigitOutputValues: List<BagOfChars>
) {
    companion object {
        fun fromInputLine(inputLine: String): Entry {
            val (uniqueSignalPatternStr, fourDigitOutputValueStr) = inputLine.split("|")
            return Entry(uniqueSignalPatternStr.toListOfBagsOfChars(), fourDigitOutputValueStr.toListOfBagsOfChars())
        }

        private fun String.toListOfBagsOfChars() =
            split(" ")
                .filter { it.isNotBlank() }
                .map { BagOfChars.fromString(it) }
    }
}

data class BagOfChars(val chars: Set<Char>) {

    val size = chars.size

    companion object {
        fun fromString(input: String) = BagOfChars(input.trim().toSet())
    }
}

