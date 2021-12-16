package org.plaehn.adventofcode.day16

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class PacketDecoderTest {

    @Test
    fun `Parse literal value`() {
        val hex = "110100101111111000101000".toHexString()
        val packet = PacketDecoder(hex).parse()

        assertThat(packet).isEqualTo(Packet(6, 4, 2021, emptyList()))

        assertThat(PacketDecoder(hex).sumOfVersionNumbersOfAllPackets()).isEqualTo(6)
    }

    @Test
    fun `Parse sub packets with total length in bits`() {
        val hex = "00111000000000000110111101000101001010010001001000000000".toHexString()
        val packet = PacketDecoder(hex).parse()

        assertThat(packet).isEqualTo(
            Packet(
                version = 1, typeId = 6, value = 0,
                subPackets = listOf(
                    Packet(version = 6, typeId = 4, value = 10, subPackets = emptyList()),
                    Packet(version = 2, typeId = 4, value = 20, subPackets = emptyList())
                )
            )
        )
        assertThat(PacketDecoder(hex).sumOfVersionNumbersOfAllPackets()).isEqualTo(9)
    }

    @Test
    fun `Parse sub packets with number of sub packets`() {
        val hex = "11101110000000001101010000001100100000100011000001100000".toHexString()
        val packet = PacketDecoder(hex).parse()

        assertThat(packet).isEqualTo(
            Packet(
                version = 7, typeId = 3, value = 0,
                subPackets = listOf(
                    Packet(version = 2, typeId = 4, value = 1, subPackets = emptyList()),
                    Packet(version = 4, typeId = 4, value = 2, subPackets = emptyList()),
                    Packet(version = 1, typeId = 4, value = 3, subPackets = emptyList())
                )
            )
        )
        assertThat(PacketDecoder(hex).sumOfVersionNumbersOfAllPackets()).isEqualTo(14)
    }

    @Test
    fun `Sum version numbers for test inputs`() {
        assertThat(PacketDecoder("8A004A801A8002F478").sumOfVersionNumbersOfAllPackets()).isEqualTo(16)
        assertThat(PacketDecoder("620080001611562C8802118E34").sumOfVersionNumbersOfAllPackets()).isEqualTo(12)
        assertThat(PacketDecoder("C0015000016115A2E0802F182340").sumOfVersionNumbersOfAllPackets()).isEqualTo(23)
        assertThat(PacketDecoder("A0016C880162017C3686B18A3D4780").sumOfVersionNumbersOfAllPackets()).isEqualTo(31)
    }

    @Test
    fun `Sum version numbers for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        assertThat(PacketDecoder(input).sumOfVersionNumbersOfAllPackets()).isEqualTo(879)
    }
}
