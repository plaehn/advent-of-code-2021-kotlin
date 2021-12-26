package org.plaehn.adventofcode.day23

import kotlin.math.absoluteValue

data class Burrow(val hallway: Hallway, val rooms: List<Room>) {

    @OptIn(ExperimentalStdlibApi::class)
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

    fun isSolution() =
        rooms.filterIndexed { index, room -> room.containsOnlyAmphipodsLivingHere(index) }.size == rooms.size

    private fun List<Room>.replace(roomIndex: Int, indexInRoom: Int, newChar: Char) =
        mapIndexed { index, room ->
            if (index == roomIndex) {
                Room(room.spots.mapIndexed { i, ch -> if (i == indexInRoom) newChar else ch })
            } else {
                room
            }
        }

    companion object {
        fun fromInputLines(inputList: List<String>): Burrow {
            val hallwayLength = inputList.drop(1).first().length - 2
            val roomsTopDown = inputList.drop(2).map { roomLine ->
                roomLine.filter { it.isOpenSpace() || it.isAmphipod() }
            }.dropLast(1)
            val rooms = (0 until roomsTopDown.first().length).map { index ->
                Room(roomsTopDown.map { it[index] })
            }
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

