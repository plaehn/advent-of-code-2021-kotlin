package org.plaehn.adventofcode.day21

import com.google.common.collect.Sets

class DiracDice(start1: Int, start2: Int) {

    private val initialGameState = GameState(PlayerState(start1), PlayerState(start2))

    fun solvePart1(): Int {
        var gameState = initialGameState
        val die = DeterministicDie()
        while (!gameState.isWinner()) {
            gameState = gameState.next(die.roll())
        }
        return gameState.minScore() * die.numberOfRolls
    }

    fun solvePart2() = playWithQuantumDie().max()

    private val quantumDieRollToFrequencyMap: Map<Int, Int> by lazy {
        val oneRoll = setOf(1, 2, 3)
        Sets.cartesianProduct(oneRoll, oneRoll, oneRoll)
            .map { threeRolls -> threeRolls.sum() }
            .groupingBy { it }
            .eachCount()
    }

    private val stateMemory: MutableMap<GameState, WinCounts> = mutableMapOf()

    private fun playWithQuantumDie(state: GameState = initialGameState): WinCounts =
        when {
            state.isWinner(scoreNeeded = 21) ->
                if (state.player1.score > state.player2.score) WinCounts(1, 0) else WinCounts(0, 1)
            state in stateMemory ->
                stateMemory.getValue(state)
            else ->
                quantumDieRollToFrequencyMap.map { (roll, frequency) ->
                    playWithQuantumDie(state.next(roll)) * frequency
                }.reduce { a, b -> a + b }.also { stateMemory[state] = it }

        }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            inputLines.map { it.split(": ")[1].toInt() }.let { (start1, start2) -> DiracDice(start1, start2) }
    }
}

data class GameState(
    val player1: PlayerState,
    val player2: PlayerState,
    val player1Turn: Boolean = true
) {

    fun next(roll: Int) =
        GameState(
            if (player1Turn) player1.advance(roll) else player1,
            if (!player1Turn) player2.advance(roll) else player2,
            player1Turn = !player1Turn
        )

    fun isWinner(scoreNeeded: Int = 1000) =
        player1.score >= scoreNeeded || player2.score >= scoreNeeded

    fun minScore(): Int = minOf(player1.score, player2.score)
}

data class PlayerState(val position: Int = 0, val score: Int = 0) {

    fun advance(steps: Int): PlayerState {
        val newPosition = (position + steps - 1) % 10 + 1
        val newScore = score + newPosition
        return PlayerState(newPosition, newScore)
    }
}

data class DeterministicDie(private var value: Int = 0, var numberOfRolls: Int = 0) {

    fun roll(): Int {
        numberOfRolls += 3
        value += 3
        return 3 * value - 3
    }
}

data class WinCounts(val player1: Long = 0, val player2: Long = 0) {

    operator fun plus(other: WinCounts) = WinCounts(player1 + other.player1, player2 + other.player2)

    operator fun times(multiplier: Int) = WinCounts(player1 * multiplier, player2 * multiplier)

    fun max() = maxOf(player1, player2)
}