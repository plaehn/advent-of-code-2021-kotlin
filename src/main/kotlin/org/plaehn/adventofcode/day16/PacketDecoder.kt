package org.plaehn.adventofcode.day16

import com.google.common.annotations.VisibleForTesting
import org.plaehn.adventofcode.common.productOf

class PacketDecoder(input: String) {

    private val binaryString = input.toBinaryString()
    private var index = 0

    fun calculateValue() =
        parse().value

    fun sumOfVersionNumbersOfAllPackets() =
        parse().let { packet -> (packet.allSubPackets + packet).sumOf { it.version } }

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
    val constantValue: Long = 0,
    val subPackets: List<Packet> = emptyList()
) {
    val allSubPackets: List<Packet>
        get() = subPackets + subPackets.flatMap { it.allSubPackets }

    val value: Long
        get() = when (typeId) {
            0 -> subPackets.sumOf { it.value }
            1 -> subPackets.productOf { it.value }
            2 -> subPackets.minOf { it.value }
            3 -> subPackets.maxOf { it.value }
            4 -> constantValue
            5 -> if (subPackets[0].value > subPackets[1].value) 1 else 0
            6 -> if (subPackets[0].value < subPackets[1].value) 1 else 0
            7 -> if (subPackets[0].value == subPackets[1].value) 1 else 0
            else -> error("Unknown typeId $typeId")
        }
}

