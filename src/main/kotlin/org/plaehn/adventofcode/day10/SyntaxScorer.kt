package org.plaehn.adventofcode.day10

import java.util.*

class SyntaxScorer(
    private val lines: List<String>
) {
    private val openingCharacters = listOf('(', '[', '{', '<')
    private val closingCharacters = listOf(')', ']', '}', '>')

    fun computeTotalSyntaxErrorScore() =
        lines
            .mapNotNull { findFirstIllegalCharacter(it) }
            .sumOf { scoreIllegalCharacter(it) }

    private fun findFirstIllegalCharacter(line: String): Char? {
        val stack = Stack<Char>()
        line.forEach { char ->
            when (char) {
                in openingCharacters -> stack.push(char)
                in closingCharacters -> {
                    val openingChar = stack.pop()
                    if (!areCompatible(openingChar, char)) {
                        return char
                    }
                }
            }
        }
        return null
    }

    private fun areCompatible(openingChar: Char, closingChar: Char) =
        openingCharacters.indexOf(openingChar) == closingCharacters.indexOf(closingChar)

    private fun scoreIllegalCharacter(illegalCharacter: Char) =
        when (illegalCharacter) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalStateException("Not an illegal character: $illegalCharacter")
        }
}



