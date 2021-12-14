package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.groupByBlankLines

class ExtendedPolymerization(
    private val polymerTemplate: String,
    insertionRules: Map<String, String>
) {
    private val transformationRules =
        insertionRules
            .map { it.key to Pair(it.key[0] + it.value, it.value + it.key[1]) }
            .toMap()

    private val initialPairCount =
        polymerTemplate
            .zipWithNext()
            .map { it.toList().joinToString("") }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

    fun computeSolution(numberOfSteps: Int): Long {
        val finalPairCount = (1..numberOfSteps).fold(initialPairCount) { pairCount, _ -> step(pairCount) }

        val charCount: MutableMap<Char, Long> =
            finalPairCount
                .entries
                .map { listOf(it.key[0] to it.value, it.key[1] to it.value) }
                .flatten()
                .groupBy { it.first }
                .map { entry -> entry.key to entry.value.sumOf { it.second } }
                .toMap().toMutableMap()

        charCount[polymerTemplate.first()] = (charCount[polymerTemplate.first()]!! - 1L)
        charCount[polymerTemplate.last()] = (charCount[polymerTemplate.last()]!! + 1L)

        val mostCommon = charCount.maxOf { it.value / 2 }
        val leastCommon = charCount.minOf { it.value / 2 }

        return mostCommon - leastCommon
    }

    private fun step(pairCount: Map<String, Long>): Map<String, Long> {
        val oldEntries: List<Pair<String, Long>> = pairCount.toList()
        val updates: List<Pair<String, Long>> =
            pairCount.map { entry ->
                val count = entry.value
                transformationRules[entry.key]!!.toList().map { it to count } + (entry.key to -count)
            }.flatten()

        val newEntries: Map<String, Long> =
            (oldEntries + updates)
                .groupBy { it.first }
                .map { entry -> entry.key to entry.value.sumOf { it.second } }
                .toMap()
        return newEntries
    }

    companion object {
        fun fromInput(input: String): ExtendedPolymerization {
            val (polymerTemplate, pairInsertions) = input.groupByBlankLines()
            val insertionRules = pairInsertions.lines().associate {
                val (from, to) = it.split(" -> ")
                from to to
            }
            return ExtendedPolymerization(polymerTemplate, insertionRules)
        }
    }
}
