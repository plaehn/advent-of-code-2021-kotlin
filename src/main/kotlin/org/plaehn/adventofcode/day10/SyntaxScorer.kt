package org.plaehn.adventofcode.day10

import java.util.*

class SyntaxScorer(
    private val lines: List<String>
) {
    private val openingCharacters = listOf('(', '[', '{', '<')
    private val closingCharacters = listOf(')', ']', '}', '>')

    fun computeTotalAutocompleteScore() =
        lines
            .filter { isIncomplete(it) }
            .map { computeSequenceOfClosingCharacters(it) }
            .map { scoreSequenceOfClosingCharacters(it) }
            .middle()

    private fun isIncomplete(it: String) = findFirstIllegalCharacter(it) == null

    private fun computeSequenceOfClosingCharacters(line: String): String {
        val stack = Stack<Char>()
        line.forEach { char ->
            when (char) {
                in openingCharacters -> stack.push(char)
                in closingCharacters -> stack.pop()
            }
        }
        return mapToCorrespondingClosingCharacters(stack)
    }

    private fun mapToCorrespondingClosingCharacters(stack: Stack<Char>) =
        stack
            .reversed()
            .map { unmatchedOpeningChar -> closingCharacters[openingCharacters.indexOf(unmatchedOpeningChar)] }
            .joinToString(separator = "")

    private fun scoreSequenceOfClosingCharacters(closingCharacters: String) =
        closingCharacters.fold(0L) { score, char ->
            score * 5 + scoreMissingClosingCharacter(char)
        }

    private fun scoreMissingClosingCharacter(closingCharacter: Char) =
        when (closingCharacter) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw IllegalStateException("Not a closing character: $closingCharacter")
        }

    private fun List<Long>.middle() = sorted()[size / 2]

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



