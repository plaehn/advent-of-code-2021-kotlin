package org.plaehn.adventofcode.day23

import java.util.*
import kotlin.math.absoluteValue

class Amphipod(private val initialBurrow: Burrow) {

    fun computeLeastEnergyToOrganizeAmphipods() = findAllCosts(initialBurrow)

    private fun findAllCosts(initialBurrow: Burrow): Int {
        val candidates = PriorityQueue<BurrowWithCost>().apply { add(BurrowWithCost(initialBurrow, 0)) }
        val visited = mutableSetOf<BurrowWithCost>()
        val currentCosts = mutableMapOf<Burrow, Int>().withDefault { Int.MAX_VALUE }

        while (candidates.isNotEmpty()) {
            val current = candidates.poll()
            visited.add(current)
            current.burrow
                .enumerateLegalMoves()
                .filter { !visited.contains(it) }
                .forEach { next ->
                    val newCost = current.cost + next.cost
                    if (newCost < currentCosts.getValue(next.burrow)) {
                        currentCosts[next.burrow] = newCost
                        candidates.add(BurrowWithCost(next.burrow, newCost))
                    }
                }
        }
        return currentCosts.entries.first { it.key.isSolution() }.value
    }

    companion object {
        fun fromInputLines(inputList: List<String>): Amphipod {
            val hallwayLength = inputList.drop(1).first().length - 2
            val roomsTopDown = inputList.drop(2).take(2).map { roomLine ->
                roomLine.filter { it.isOpenSpace() || it.isAmphipod() }
            }
            val rooms = roomsTopDown.first()
                .zip(roomsTopDown.last())
                .map { Room(it.first, it.second) }
            val hallway = Hallway(List(hallwayLength) { '.' })
            return Amphipod(Burrow(hallway, rooms))
        }
    }
}

data class BurrowWithCost(val burrow: Burrow, val cost: Int) : Comparable<BurrowWithCost> {

    override fun compareTo(other: BurrowWithCost) = cost.compareTo(other.cost)
}

private fun Char.costOfOneStep() =
    when (this) {
        'A' -> 1
        'B' -> 10
        'C' -> 100
        'D' -> 1000
        else -> error("Unknown amphipod")
    }

private fun List<Room>.replace(roomIndex: Int, indexInRoom: Int, newChar: Char) =
    mapIndexed { index, room ->
        if (index == roomIndex) {
            if (indexInRoom == 0) Room(newChar, room.lower) else Room(room.upper, newChar)
        } else {
            room
        }
    }

data class Burrow(val hallway: Hallway, val rooms: List<Room>) {

    fun enumerateLegalMoves(): List<BurrowWithCost> {
        val allMoves = mutableListOf<BurrowWithCost>()
        allMoves.addAll(movesFromHallwayIntoRoom())
        allMoves.addAll(movesFromRoomIntoRoom())
        allMoves.addAll(movesFromRoomIntoHallway())
        return allMoves
    }

    private fun movesFromRoomIntoHallway() =
        sequence {
            rooms.forEachIndexed { roomIndex, room ->
                if (room.isNotEmpty() && room.canRemoveTopmostAmphipod(roomIndex)) {
                    val (amphipod, indexInRoom) = room.topmostNonEmptyWithIndex()
                    val hallwayIndexAboveRoom = computeHallwayIndexAboveRoom(roomIndex)
                    hallway.findEmptySpotsReachableFrom(hallwayIndexAboveRoom).forEach { hallwayIndex ->
                        val newBurrow = Burrow(
                            hallway.replace(hallwayIndex, amphipod),
                            rooms.replace(roomIndex, indexInRoom, '.')
                        )
                        val numberOfSteps = (hallwayIndexAboveRoom - hallwayIndex).absoluteValue + indexInRoom + 1
                        val costOfMove = numberOfSteps * amphipod.costOfOneStep()
                        yield(BurrowWithCost(newBurrow, costOfMove))
                    }
                }
            }
        }.toList()

    private fun movesFromHallwayIntoRoom(): Collection<BurrowWithCost> {
        return sequence {
            hallway.spots.forEachIndexed { hallwayIndex, ch ->
                if (ch.isAmphipod()) {
                    rooms.forEachIndexed { roomIndex, room ->
                        val indexInRoom = room.hasRoomForAtIndex(ch, roomIndex)
                        val hallwayIndexAboveRoom = computeHallwayIndexAboveRoom(roomIndex)
                        if (indexInRoom != null && isReachable(hallwayIndex, hallwayIndexAboveRoom)) {
                            val newBurrow = Burrow(
                                hallway.replace(hallwayIndex, '.'),
                                rooms.replace(roomIndex, indexInRoom, ch)
                            )
                            val numberOfSteps = (hallwayIndexAboveRoom - hallwayIndex).absoluteValue + indexInRoom + 1
                            val costOfMove = numberOfSteps * ch.costOfOneStep()
                            yield(BurrowWithCost(newBurrow, costOfMove))
                        }
                    }
                }
            }
        }.toList()
    }

    private fun movesFromRoomIntoRoom(): Collection<BurrowWithCost> {
        return sequence {
            rooms.forEachIndexed { startRoomIndex, startRoom ->
                if (startRoom.isNotEmpty()) {
                    rooms.forEachIndexed { endRoomIndex, endRoom ->
                        if (startRoom != endRoom && startRoom.canRemoveTopmostAmphipod(endRoomIndex)) {
                            val (amphipod, indexInStartRoom) = startRoom.topmostNonEmptyWithIndex()
                            val hallwayIndexAboveStart = computeHallwayIndexAboveRoom(startRoomIndex)
                            val hallwayIndexAboveEnd = computeHallwayIndexAboveRoom(endRoomIndex)
                            val indexInEndRoom = endRoom.hasRoomForAtIndex(amphipod, endRoomIndex)
                            if (isReachable(hallwayIndexAboveStart, hallwayIndexAboveEnd) && indexInEndRoom != null) {
                                val newBurrow = Burrow(
                                    hallway,
                                    rooms
                                        .replace(startRoomIndex, indexInStartRoom, '.')
                                        .replace(endRoomIndex, indexInEndRoom, amphipod)
                                )
                                val numberOfSteps = (hallwayIndexAboveStart - hallwayIndexAboveEnd).absoluteValue +
                                        indexInStartRoom + indexInEndRoom + 2
                                val costOfMove = numberOfSteps * amphipod.costOfOneStep()
                                yield(BurrowWithCost(newBurrow, costOfMove))
                            }
                        }
                    }
                }
            }
        }.toList()
    }

    private fun computeHallwayIndexAboveRoom(roomIndex: Int) = 2 * roomIndex + 2

    private fun isReachable(hallwayIndex: Int, hallwayIndexAboveRoom: Int) =
        if (hallwayIndex < hallwayIndexAboveRoom) {
            (hallwayIndex + 1 until hallwayIndexAboveRoom).all { this.hallway.spots[it].isOpenSpace() }
        } else {
            (hallwayIndexAboveRoom + 1 until hallwayIndex).all { this.hallway.spots[it].isOpenSpace() }
        }

    fun isSolution() = rooms.map { "${it.upper}${it.lower}" } == listOf("AA", "BB", "CC", "DD")

    override fun toString(): String {
        var str = '#'.repeat(hallway.size + 2) + '\n'
        str += "#$hallway#\n"
        str += '#'.repeat(3)
        str += rooms.joinToString("") { it.upper + "#" } + "##\n"
        str += "  #" + rooms.joinToString("") { it.lower + "#" } + "##\n"
        str += "  " + '#'.repeat(hallway.size - 2)
        return str
    }
}

private fun Char.repeat(number: Int) = (1..number).map { this }.joinToString("")

private fun Char.isOpenSpace(): Boolean = this == '.'

private fun Char.isAmphipod(): Boolean = this == 'A' || this == 'B' || this == 'C' || this == 'D'

data class Hallway(val spots: List<Char>) {
    val size = spots.size

    override fun toString() = spots.joinToString("")

    fun findEmptySpotsReachableFrom(startIndex: Int) =
        sequence {
            var index = startIndex
            do {
                --index
                if (index >= 0 && spots[index].isOpenSpace()) yield(index)
            } while (index >= 0 && spots[index].isOpenSpace())
            index = startIndex
            do {
                ++index
                if (index < spots.size && spots[index].isOpenSpace()) yield(index)
            } while (index < spots.size && spots[index].isOpenSpace())
        }
            .filter { !it.isHallwayIndexAboveRoom() }
            .toList()

    fun replace(indexToReplace: Int, newChar: Char) =
        Hallway(spots.mapIndexed { index, char ->
            if (index == indexToReplace) newChar else char
        })
}

private fun Int.isHallwayIndexAboveRoom() = this == 2 || this == 4 || this == 6 || this == 8

data class Room(val upper: Char, val lower: Char) {

    fun topmostNonEmptyWithIndex() = if (upper != '.') upper to 0 else lower to 1

    fun isNotEmpty() = upper != '.' || lower != '.'

    fun hasRoomForAtIndex(amphipod: Char, roomIndex: Int): Int? {
        val isCorrectPosition = 'A' + roomIndex == amphipod
        return when {
            isCorrectPosition && upper.isOpenSpace() && lower.isOpenSpace() -> 1
            isCorrectPosition && upper.isOpenSpace() && lower == amphipod -> 0
            else -> null
        }
    }

    fun canRemoveTopmostAmphipod(roomIndex: Int): Boolean {
        val amphipodLivingInThisRoom = 'A' + roomIndex
        return !((upper == amphipodLivingInThisRoom && lower == amphipodLivingInThisRoom)
                || (upper.isOpenSpace() && lower == amphipodLivingInThisRoom))
    }
}
