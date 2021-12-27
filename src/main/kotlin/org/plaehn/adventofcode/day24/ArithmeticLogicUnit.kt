package org.plaehn.adventofcode.day24

import java.time.LocalDateTime

class ArithmeticLogicUnit(instructions: List<Instruction>) {

    private val instructionBlocks: List<List<Instruction>> = groupByInp(instructions)

    private fun groupByInp(instructions: List<Instruction>) =
        sequence {
            val group = mutableListOf<Instruction>()
            instructions.forEach { instr ->
                if (instr.operator == "inp" && group.isNotEmpty()) {
                    yield(group.toList())
                    group.clear()
                }
                group.add(instr)
            }
            yield(group)
        }.toList()

    fun computeLargestAcceptedModelNumber() =
        traverse(emptyList(), Alu()) ?: error("No model number found")

    private fun traverse(modelNumber: List<Int>, alu: Alu): Long? {
        if (modelNumber.size == 14) {
            return if (alu['z'] == 0L) modelNumber.toLong() else null
        }
        if (modelNumber.size == 1) println(LocalDateTime.now().toString() + ": " + modelNumber.first())
        return (9 downTo 1).firstNotNullOfOrNull { digit ->
            val resultAlu = execute(instructionBlocks[modelNumber.size], alu, digit)
            traverse(modelNumber + digit, resultAlu)
        }
    }

    private fun execute(
        instructions: List<Instruction>,
        inputAlu: Alu,
        nextDigit: Int
    ) =
        instructions.fold(inputAlu) { alu, instr ->

            val rhs = if (instr.rhsIsVariable()) {
                alu[instr.rhsAsVariable()]
            } else {
                instr.rhsAsNumber()
            }

            val value = when (instr.operator) {
                "inp" -> nextDigit.toLong()
                "add" -> alu[instr.variable] + rhs
                "mul" -> alu[instr.variable] * rhs
                "div" -> alu[instr.variable] / rhs
                "mod" -> alu[instr.variable] % rhs
                "eql" -> if (alu[instr.variable] == rhs) 1 else 0
                else -> error("Unknown operand ${instr.operator}")
            }
            val newAlu = alu.set(instr.variable, value)
            newAlu
        }

    private fun List<Int>.toLong() =
        fold(0L) { acc, elem -> acc * 10 + elem }

    companion object {
        fun fromInputLines(inputList: List<String>) =
            ArithmeticLogicUnit(inputList.map { Instruction.fromInputString(it) })
    }
}

data class Instruction(val operator: String, val variable: Char, val rhs: String?) {

    fun rhsIsVariable() = rhs != null && rhs.first().isLetter()
    fun rhsAsVariable() = rhs!!.first()
    fun rhsAsNumber() = rhs?.toLong() ?: -1

    override fun toString() = (operator + " " + variable + " " + rhs.orEmpty()).trim()

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
