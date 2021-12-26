package org.plaehn.adventofcode.day23

data class Room(val spots: List<Char>) {

    fun topmostNonEmptyWithIndex() = if (spots[0] != '.') spots[0] to 0 else spots[1] to 1

    fun isNotEmpty() = spots[0] != '.' || spots[1] != '.'

    fun hasRoomForAmphipodAtIndex(amphipod: Char, roomIndex: Int): Int? {
        val isCorrectPosition = 'A' + roomIndex == amphipod
        return when {
            isCorrectPosition && spots[0].isOpenSpace() && spots[1].isOpenSpace() -> 1
            isCorrectPosition && spots[0].isOpenSpace() && spots[1] == amphipod -> 0
            else -> null
        }
    }

    fun canRemoveTopmostAmphipod(roomIndex: Int): Boolean {
        val amphipodLivingInThisRoom = 'A' + roomIndex
        return !((spots[0] == amphipodLivingInThisRoom && spots[1] == amphipodLivingInThisRoom)
                || (spots[0].isOpenSpace() && spots[1] == amphipodLivingInThisRoom))
    }
}