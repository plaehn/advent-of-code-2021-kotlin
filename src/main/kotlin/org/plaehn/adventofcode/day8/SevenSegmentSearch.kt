package org.plaehn.adventofcode.day8

import com.google.common.collect.Collections2.permutations

class SevenSegmentSearch(
    private val entries: List<Entry>
) {

    private val segments2Digit = mapOf(
        BagOfChars.fromString("abcefg") to 0,
        BagOfChars.fromString("cf") to 1,
        BagOfChars.fromString("acdeg") to 2,
        BagOfChars.fromString("acdfg") to 3,
        BagOfChars.fromString("bcdf") to 4,
        BagOfChars.fromString("abdfg") to 5,
        BagOfChars.fromString("abdefg") to 6,
        BagOfChars.fromString("acf") to 7,
        BagOfChars.fromString("abcdefg") to 8,
        BagOfChars.fromString("abcdfg") to 9
    )

    private val easyDigitSizes = segments2Digit.keys.findUniqueSizes()

    private val allMappings by lazy { computeAllMappings() }

    fun computeSumOfOutputNumbers() = entries.sumOf { findOutputNumber(it) }

    private fun findOutputNumber(entry: Entry): Int {
        val mapping = findMapping(entry.uniqueSignalPatterns)
        val digits = entry.fourDigitOutputValues
            .map { it.apply(mapping) }
            .map { segments2Digit[it]!! }
        return digits.fold(0) { number, digit -> number * 10 + digit }
    }

    private fun findMapping(uniqueSignalPatterns: List<BagOfChars>) =
        allMappings.first { mapping ->
            uniqueSignalPatterns
                .map { it.apply(mapping) }
                .all { segments2Digit.contains(it) }
        }

    private fun computeAllMappings(): List<Map<Char, Char>> {
        val chars = "abcdefg".toList()
        return permutations(chars).map { it.zip(chars).toMap() }
    }

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

    fun apply(mapping: Map<Char, Char>) = BagOfChars(chars.map { mapping[it]!! }.toSet())

    companion object {
        fun fromString(input: String) = BagOfChars(input.trim().toSet())
    }
}

