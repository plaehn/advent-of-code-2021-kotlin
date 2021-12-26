package org.plaehn.adventofcode.day24

class ArithmeticLogicUnit(private val instructions: List<String>) {

    fun computeLargestAcceptedModelNumber(): Long {
        TODO()
    }

    internal fun execute(input: MutableList<Int>) =
        instructions.fold(Alu()) { alu, instr ->
            val parts = instr.split(" ")
            val op = parts[0]
            val variable = parts[1].first()
            val rhsStr = parts.getOrNull(2) ?: "1"
            val rhs = if (rhsStr.first().isLetter()) alu[rhsStr.first()] else rhsStr.toInt()
            val value = when (op) {
                "inp" -> input.removeFirst()
                "add" -> alu[variable] + rhs
                "mul" -> alu[variable] * rhs
                "div" -> alu[variable] / rhs
                "mod" -> alu[variable] % rhs
                "eql" -> if (alu[variable] == rhs) 1 else 0
                else -> error("Unknown operand $op")
            }
            alu.set(variable, value)
        }

    companion object {
        fun fromInputLines(inputList: List<String>) = ArithmeticLogicUnit(inputList)
    }
}

data class Alu(private val variables: Map<Char, Int> = mapOf('x' to 0, 'y' to 0, 'z' to 0, 'w' to 0)) {

    fun set(variable: Char, value: Int) =
        Alu(variables.toMutableMap().apply { this[variable] = value })

    operator fun get(op: Char) = variables[op] ?: 0
}
