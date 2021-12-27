package org.plaehn.adventofcode.day24

import kotlin.streams.toList

class ArithmeticLogicUnit(private val instructions: List<Instruction>) {

    // group instructions into blocks starting with "inp"
    // keep cache mapping number of processed "inp" group with input list prefix of same length

    fun computeLargestAcceptedModelNumber(): Long {
        var count = 0
        return modelNumbers().first {
            val result = execute(it.toMutableList())
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
        input: MutableList<Int>
    ) =
        instructions.foldIndexed(Alu()) { index, alu, instr ->

            val rhs = if (instr.rhs != null) {
                if (instr.rhs.first().isLetter()) {
                    alu[instr.rhs.first()]
                } else {
                    instr.rhs.toLong()
                }
            } else {
                null
            }
            val value = when (instr.operator) {
                "inp" -> input.removeFirst().toLong()
                "add" -> alu[instr.variable] + rhs!!
                "mul" -> alu[instr.variable] * rhs!!
                "div" -> alu[instr.variable] / rhs!!
                "mod" -> alu[instr.variable] % rhs!!
                "eql" -> if (alu[instr.variable] == rhs) 1 else 0
                else -> error("Unknown operand ${instr.operator}")
            }
            val newAlu = alu.set(instr.variable, value)
            newAlu
        }

    companion object {
        fun fromInputLines(inputList: List<String>) =
            ArithmeticLogicUnit(inputList.map { Instruction.fromInputString(it) })
    }
}

data class Instruction(val operator: String, val variable: Char, val rhs: String?) {

    companion object {
        fun fromInputString(input: String) =
            input.split(" ").let { Instruction(it[0], it[1].first(), it.getOrNull(2)) }
    }
}

data class Alu(private val variables: Map<Char, Long> = mapOf('x' to 0, 'y' to 0, 'z' to 0, 'w' to 0)) {

    fun set(variable: Char, value: Long) =
        Alu(variables.toMutableMap().apply { this[variable] = value })

    operator fun get(op: Char) = variables[op]!!
}
