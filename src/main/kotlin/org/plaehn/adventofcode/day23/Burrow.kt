@file:OptIn(ExperimentalStdlibApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package org.plaehn.adventofcode.day23

import kotlin.math.absoluteValue

data class Burrow(val hallway: Hallway, val rooms: List<Room>) {

    fun enumerateLegalMoves() =
        buildList {
            addAll(movesFromHallwayIntoRoom())
            addAll(movesFromRoomIntoRoom())
            addAll(movesFromRoomIntoHallway())
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

    private fun movesFromHallwayIntoRoom() =
        sequence {
            hallway.spots.forEachIndexed { hallwayIndex, ch ->
                if (ch.isAmphipod()) {
                    rooms.forEachIndexed { roomIndex, room ->
                        val indexInRoom = room.hasRoomForAmphipodAtIndex(ch, roomIndex)
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

    private fun movesFromRoomIntoRoom() =
        sequence {
            rooms.forEachIndexed { startRoomIndex, startRoom ->
                if (startRoom.isNotEmpty()) {
                    rooms.forEachIndexed { endRoomIndex, endRoom ->
                        if (startRoom != endRoom && startRoom.canRemoveTopmostAmphipod(endRoomIndex)) {
                            val (amphipod, indexInStartRoom) = startRoom.topmostNonEmptyWithIndex()
                            val hallwayIndexAboveStart = computeHallwayIndexAboveRoom(startRoomIndex)
                            val hallwayIndexAboveEnd = computeHallwayIndexAboveRoom(endRoomIndex)
                            val indexInEndRoom = endRoom.hasRoomForAmphipodAtIndex(amphipod, endRoomIndex)
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

    private fun computeHallwayIndexAboveRoom(roomIndex: Int) = 2 * roomIndex + 2

    private fun isReachable(hallwayIndex: Int, hallwayIndexAboveRoom: Int) =
        if (hallwayIndex < hallwayIndexAboveRoom) {
            (hallwayIndex + 1 until hallwayIndexAboveRoom).all { this.hallway.spots[it].isOpenSpace() }
        } else {
            (hallwayIndexAboveRoom + 1 until hallwayIndex).all { this.hallway.spots[it].isOpenSpace() }
        }

    fun isSolution() = rooms.map { "${it.upper}${it.lower}" } == listOf("AA", "BB", "CC", "DD")

    override fun toString(): String {
        var str = '#'.repeat(hallway.spots.size + 2) + '\n'
        str += "#$hallway#\n"
        str += '#'.repeat(3)
        str += rooms.joinToString("") { it.upper + "#" } + "##\n"
        str += "  #" + rooms.joinToString("") { it.lower + "#" } + "##\n"
        str += "  " + '#'.repeat(hallway.spots.size - 2)
        return str
    }

    private fun Char.repeat(number: Int) = (1..number).map { this }.joinToString("")

    private fun List<Room>.replace(roomIndex: Int, indexInRoom: Int, newChar: Char) =
        mapIndexed { index, room ->
            if (index == roomIndex) {
                if (indexInRoom == 0) Room(newChar, room.lower) else Room(room.upper, newChar)
            } else {
                room
            }
        }

    companion object {
        fun fromInputLines(inputList: List<String>): Burrow {
            val hallwayLength = inputList.drop(1).first().length - 2
            val roomsTopDown = inputList.drop(2).take(2).map { roomLine ->
                roomLine.filter { it.isOpenSpace() || it.isAmphipod() }
            }
            val rooms = roomsTopDown.first()
                .zip(roomsTopDown.last())
                .map { Room(it.first, it.second) }
            val hallway = Hallway(List(hallwayLength) { '.' })
            return Burrow(hallway, rooms)
        }
    }
}

private fun Char.costOfOneStep() =
    when (this) {
        'A' -> 1
        'B' -> 10
        'C' -> 100
        'D' -> 1000
        else -> error("Unknown amphipod")
    }

