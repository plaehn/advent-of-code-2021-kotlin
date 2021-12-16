package org.plaehn.adventofcode.day16

import com.google.common.annotations.VisibleForTesting

class PacketDecoder(input: String) {

    private val binaryString = input.toBinaryString()
    private var index = 0

    fun sumOfVersionNumbersOfAllPackets(): Int {
        val packet = parse()
        return (packet.allSubPackets + packet).sumOf { it.version }
    }

    @VisibleForTesting
    internal fun parse(): Packet {
        val version = consume(3).toInt(2)
        val typeId = consume(3).toInt(2)
        return when (typeId) {
            4 -> Packet(version, typeId, parseLiteralValue())
            else -> Packet(version, typeId, 0, parseSubPackets())
        }
    }

    private fun parseLiteralValue() =
        sequence {
            do {
                val endMarker = consume(1)
                yield(consume(4))
            } while (endMarker == "1")
        }.toList().joinToString("").toLong(2)

    private fun parseSubPackets(): List<Packet> {
        val lengthTypeId = consume(1)
        return when (lengthTypeId) {
            "0" -> parseWithTotalLengthInBits()
            "1" -> parseWithNumberOfSubPackets()
            else -> error("Unknown lengthTypeId $lengthTypeId")
        }
    }

    private fun parseWithTotalLengthInBits(): List<Packet> {
        val totalLength = consume(15).toInt(2)
        val endIndex = index + totalLength
        return sequence {
            do {
                yield(parse())
            } while (index < endIndex)
        }.toList()
    }

    private fun parseWithNumberOfSubPackets(): List<Packet> {
        val numberOfSubPackages = consume(11).toInt(2)
        return (1..numberOfSubPackages).map { parse() }
    }

    private fun consume(n: Int): String {
        val consumedString = binaryString.substring(index, index + n)
        index += n
        return consumedString
    }
}

private fun String.toBinaryString() =
    map { hexChar ->
        hexChar.digitToInt(16).toString(2).padStart(4, '0')
    }.joinToString("")

@VisibleForTesting
internal fun String.toHexString() =
    chunked(4).joinToString("") {
        it.toInt(2).toString(16)
    }

data class Packet(
    val version: Int,
    val typeId: Int,
    val value: Long = 0,
    val subPackets: List<Packet> = emptyList()
) {
    val allSubPackets: List<Packet>
        get() = subPackets + subPackets.flatMap { it.allSubPackets }
}
