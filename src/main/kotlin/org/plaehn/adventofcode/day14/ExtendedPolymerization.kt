package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.groupByBlankLines

class ExtendedPolymerization(
    private val polymerTemplate: String,
    private val insertionRules: Map<String, String>
) {

    fun computeSolution(): Int {
        val elementCounts = (1..10)
            .fold(polymerTemplate) { polymerTemplate, _ -> step(polymerTemplate) }
            .groupingBy { it }
            .eachCount()

        val mostCommon = elementCounts.maxOf { it.value }
        val leastCommon = elementCounts.minOf { it.value }

        return mostCommon - leastCommon
    }

    private fun step(polymerTemplate: String) =
        polymerTemplate
            .zipWithNext()
            .map { it.toList().joinToString("") }
            .joinToString("") { it[0] + insertionRules[it].toString() } + polymerTemplate.last()

    companion object {
        fun fromInput(input: String): ExtendedPolymerization {
            val (polymerTemplate, pairInsertions) = input.groupByBlankLines()
            val insertionRules = pairInsertions.lines().map {
                val (from, to) = it.split(" -> ")
                from to to
            }.toMap()
            return ExtendedPolymerization(polymerTemplate, insertionRules)
        }
    }
}
