package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.combinations
import kotlin.math.ceil
import kotlin.math.floor

class Snailfish(private val numbersToSum: List<SnailFishNumber>) {

    fun computeMagnitudeOfFinalSum() =
        numbersToSum
            .reduce(SnailFishNumber::addAndReduce)
            .magnitude()

    fun computeMaxMagnitude() =
        allPairs()
            .map { it.first.addAndReduce(it.second).magnitude() }
            .maxOrNull()!!

    private fun allPairs() =
        sequence {
            numbersToSum.toSet().combinations(2).forEach { pair ->
                yield(pair.first() to pair.last())
                yield(pair.last() to pair.first())
            }
        }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            Snailfish(inputLines.map { SnailFishNumber(it) })
    }
}

data class SnailFishNumber(val surface: String) {

    fun addAndReduce(summand: SnailFishNumber) = (this + summand).reduce()

    operator fun plus(summand: SnailFishNumber) =
        SnailFishNumber("[" + this.surface + "," + summand.surface + "]")

    fun reduce(): SnailFishNumber {
        var str = surface
        do {
            str = tryExplode(str) ?: trySplit(str) ?: return SnailFishNumber(str)
        } while (true)
    }

    private fun tryExplode(str: String): String? {
        var nestingDepth = 0
        str.forEachIndexed { index, ch ->
            when (ch) {
                '[' -> if (++nestingDepth == 5) return str.explode(str.pairStartingAt(index), index)
                ']' -> --nestingDepth
            }
        }
        return null
    }

    private fun String.pairStartingAt(startIndex: Int): String {
        var nestingDepth = 0
        substring(startIndex).forEachIndexed { index, ch ->
            when (ch) {
                '[' -> ++nestingDepth
                ']' -> if (--nestingDepth == 0) return substring(startIndex, startIndex + index + 1)
            }
        }
        error("Found no pair")
    }

    private fun String.explode(pair: String, index: Int): String {
        val regularPair = regularPairRegex.matchEntire(pair) ?: error("Explode on non-regular pair")
        val (left, right) = regularPair.destructured.toList().map { it.toInt() }

        val numToRight = numberRegex.find(this, startIndex = index + regularPair.value.length)
        val numToLeft = numberRegex.find(this.reversed(), startIndex = length - index - 1)

        var newStr = ""
        if (numToLeft != null) {
            newStr += substring(0, this.length - numToLeft.range.last - 1)
            newStr += left + numToLeft.value.reversed().toInt()
            newStr += substring(this.length - numToLeft.range.first, index)
        } else {
            newStr += substring(0, index)
        }
        newStr += "0"
        if (numToRight != null) {
            newStr += substring(index + regularPair.value.length, numToRight.range.first)
            newStr += right + numToRight.value.toInt()
            newStr += substring(numToRight.range.last + 1)
        } else {
            newStr += substring(index + regularPair.value.length)
        }
        return newStr
    }

    private fun trySplit(str: String): String? {
        val regularNumberGreaterNine = numberGreaterNineRegex.find(str)
        if (regularNumberGreaterNine != null) {
            return str.split(regularNumberGreaterNine.value.toInt(), regularNumberGreaterNine.range.first)
        }
        return null
    }

    private fun String.split(regularNumber: Int, index: Int): String {
        val left = floor(regularNumber / 2.0).toInt()
        val right = ceil(regularNumber / 2.0).toInt()
        val replacement = "[$left,$right]"
        return replaceRange(index, index + regularNumber.toString().length, replacement)
    }

    fun magnitude(): Int {
        var str = this.surface
        while (true) {
            val regular = findFirstRegularPair(str) ?: break
            val (left, right) = regular.destructured
            val magnitude = 3 * left.toInt() + 2 * right.toInt()
            str = str.replace(regular.value, magnitude.toString())
        }
        return str.toInt()
    }

    private fun findFirstRegularPair(str: String) = regularPairRegex.find(str)

    companion object {
        private val regularPairRegex = "\\[(\\d+),(\\d+)]".toRegex()
        private val numberRegex = "\\d+".toRegex()
        private val numberGreaterNineRegex = "\\d{2,}".toRegex()
    }
}

