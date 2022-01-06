package org.plaehn.adventofcode.day21

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

    fun solvePart2(): Long {
        TODO()
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

    fun next(die: Int) =
        GameState(
            if (player1Turn) player1.advance(die) else player1,
            if (!player1Turn) player2.advance(die) else player2,
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