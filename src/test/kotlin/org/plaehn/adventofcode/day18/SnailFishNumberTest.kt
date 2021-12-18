package org.plaehn.adventofcode.day18

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SnailFishNumberTest {

    @Test
    fun plus() {
        assertThat(SnailFishNumber("[1,2]") + SnailFishNumber("[[3,4],5]")).isEqualTo(SnailFishNumber("[[1,2],[[3,4],5]]"))
    }

    @Test
    fun magnitude() {
        assertThat(SnailFishNumber("[9,1]").magnitude()).isEqualTo(29)
        assertThat(SnailFishNumber("[[1,2],[[3,4],5]]").magnitude()).isEqualTo(143)
        assertThat(SnailFishNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude()).isEqualTo(1384)
        assertThat(SnailFishNumber("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude()).isEqualTo(445)
        assertThat(SnailFishNumber("[[[[3,0],[5,3]],[4,4]],[5,5]]").magnitude()).isEqualTo(791)
        assertThat(SnailFishNumber("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude()).isEqualTo(1137)
        assertThat(SnailFishNumber("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude()).isEqualTo(3488)
    }

    @Test
    fun reduceExplode() {
        assertThat(SnailFishNumber("[[[[[9,8],1],2],3],4]").reduce()).isEqualTo(SnailFishNumber("[[[[0,9],2],3],4]"))
        assertThat(SnailFishNumber("[7,[6,[5,[4,[3,2]]]]]").reduce()).isEqualTo(SnailFishNumber("[7,[6,[5,[7,0]]]]"))
        assertThat(SnailFishNumber("[[6,[5,[4,[3,2]]]],1]").reduce()).isEqualTo(SnailFishNumber("[[6,[5,[7,0]]],3]"))
        assertThat(SnailFishNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").reduce()).isEqualTo(SnailFishNumber("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"))
        assertThat(SnailFishNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").reduce()).isEqualTo(SnailFishNumber("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"))
    }
    
    @Test
    fun reduceSplit() {
        assertThat(SnailFishNumber("[10,4]").reduce()).isEqualTo(SnailFishNumber("[[5,5],4]"))
        assertThat(SnailFishNumber("[4,11]").reduce()).isEqualTo(SnailFishNumber("[4,[5,6]]"))
    }

    @Test
    fun addAndReduce1() {
        val result = SnailFishNumber("[[[[4,3],4],4],[7,[[8,4],9]]]").addAndReduce(SnailFishNumber("[1,1]"))
        assertThat(result).isEqualTo(SnailFishNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"))
    }

    @Test
    fun addAndReduce2() {
        val result = SnailFishNumber("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
            .addAndReduce(SnailFishNumber("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"))
        assertThat(result).isEqualTo(SnailFishNumber("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"))
    }

    @Test
    fun addAndReduce3() {
        val result = SnailFishNumber("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]")
            .addAndReduce(SnailFishNumber("[2,9]"))
        assertThat(result).isEqualTo(SnailFishNumber("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"))
    }
}