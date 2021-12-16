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
                version = 1, typeId = 6, constantValue = 0,
                subPackets = listOf(
                    Packet(version = 6, typeId = 4, constantValue = 10, subPackets = emptyList()),
                    Packet(version = 2, typeId = 4, constantValue = 20, subPackets = emptyList())
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
                version = 7, typeId = 3, constantValue = 0,
                subPackets = listOf(
                    Packet(version = 2, typeId = 4, constantValue = 1, subPackets = emptyList()),
                    Packet(version = 4, typeId = 4, constantValue = 2, subPackets = emptyList()),
                    Packet(version = 1, typeId = 4, constantValue = 3, subPackets = emptyList())
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

    @Test
    fun `Calculate value for test inputs`() {
        assertThat(PacketDecoder("C200B40A82").calculateValue()).isEqualTo(3)
        assertThat(PacketDecoder("04005AC33890").calculateValue()).isEqualTo(54)
        assertThat(PacketDecoder("880086C3E88112").calculateValue()).isEqualTo(7)
        assertThat(PacketDecoder("CE00C43D881120").calculateValue()).isEqualTo(9)
        assertThat(PacketDecoder("D8005AC2A8F0").calculateValue()).isEqualTo(1)
        assertThat(PacketDecoder("F600BC2D8F").calculateValue()).isEqualTo(0)
        assertThat(PacketDecoder("9C005AC2F8F0").calculateValue()).isEqualTo(0)
        assertThat(PacketDecoder("9C0141080250320F1802104A08").calculateValue()).isEqualTo(1)
    }

    @Test
    fun `Calculate value for puzzle inputs`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        assertThat(PacketDecoder(input).calculateValue()).isEqualTo(539051801941)
    }
}
