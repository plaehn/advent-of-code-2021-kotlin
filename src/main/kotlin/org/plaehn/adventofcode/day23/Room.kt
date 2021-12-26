package org.plaehn.adventofcode.day23

data class Room(val upper: Char, val lower: Char) {

    fun topmostNonEmptyWithIndex() = if (upper != '.') upper to 0 else lower to 1

    fun isNotEmpty() = upper != '.' || lower != '.'

    fun hasRoomForAmphipodAtIndex(amphipod: Char, roomIndex: Int): Int? {
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