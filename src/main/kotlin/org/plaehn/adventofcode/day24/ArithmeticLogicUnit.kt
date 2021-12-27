package org.plaehn.adventofcode.day24

import kotlin.streams.toList

class ArithmeticLogicUnit(private val instructions: List<String>) {

    // group instructions into blocks starting with "inp"
    // keep cache mapping number of processed "inp" group with input list prefix of same length

    fun computeLargestAcceptedModelNumber(): Long {
        val cache = mutableMapOf<CacheKey, Alu>()
        var count = 0
        return modelNumbers().first {
            val result = execute(it.toMutableList(), cache)
            count++
            if (count % 10000 == 0) println(count)
            result['z'] == 0L
        }.toLong()
    }

    private fun modelNumbers() =
        generateSequence(99999999999999) { it - 1 }
            .map { it.toDigitList() }
            .filter { !it.contains(0) }

    private fun List<Int>.toLong() =
        fold(0L) { acc, elem -> acc * 10 + elem }


    private fun Long.toDigitList() =
        toString().chars().map { it - '0'.code }.toList()

    internal fun execute(
        input: MutableList<Int>,
        cache: MutableMap<CacheKey, Alu> = mutableMapOf()
    ) =
        instructions.foldIndexed(Alu()) { index, alu, instr ->
//            if (cache.containsKey(CacheKey(index, alu, input))) {
//                println("cache hit")
//                return cache[CacheKey(index, alu, input)]!!
//            }

            val parts = instr.split(" ")
            val op = parts[0]
            val variable = parts[1].first()
            val rhsStr = parts.getOrNull(2) ?: "1"
            val rhs = if (rhsStr.first().isLetter()) alu[rhsStr.first()] else rhsStr.toLong()
            val value = when (op) {
                "inp" -> input.removeFirst().toLong()
                "add" -> alu[variable] + rhs
                "mul" -> alu[variable] * rhs
                "div" -> alu[variable] / rhs
                "mod" -> alu[variable] % rhs
                "eql" -> if (alu[variable] == rhs) 1 else 0
                else -> error("Unknown operand $op")
            }
            val newAlu = alu.set(variable, value)
            // cache[CacheKey(index, alu, input)] = newAlu
            // println(instr.padEnd(12) + newAlu)
            newAlu
        }

    companion object {
        fun fromInputLines(inputList: List<String>) = ArithmeticLogicUnit(inputList)
    }
}

data class CacheKey(val instructionNumber: Int, val alu: Alu, val remainingInput: List<Int>)

data class Alu(private val variables: Map<Char, Long> = mapOf('x' to 0, 'y' to 0, 'z' to 0, 'w' to 0)) {

    fun set(variable: Char, value: Long) =
        Alu(variables.toMutableMap().apply { this[variable] = value })

    operator fun get(op: Char) = variables[op]!!
}
