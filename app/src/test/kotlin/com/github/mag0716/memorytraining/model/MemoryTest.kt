package com.github.mag0716.memorytraining.model

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by mag0716 on 2017/06/22.
 */
class MemoryTest {

    @Test
    fun levelUp_次のレベルに上がったらcountはクリアされていること() {
        val memory = Memory(0, "question", "answer", 0, 1)
        memory.levelUp(1)

        assertThat(memory.level, `is`(1))
        assertThat(memory.count, `is`(0))
    }

    @Test
    fun levelUp_同じレベルだったらcountがインクリメントされていること() {
        val memory = Memory(0, "question", "answer", 0, 0)
        memory.levelUp(0)

        assertThat(memory.level, `is`(0))
        assertThat(memory.count, `is`(1))
    }

    @Test
    fun levelDown_前のレベルに下がったらcountはクリアされていること() {
        val memory = Memory(0, "question", "answer", 1, 0)
        memory.levelDown(0)

        assertThat(memory.level, `is`(0))
        assertThat(memory.count, `is`(0))
    }

    @Test
    fun levelDown_同じレベルだったらcountがクリアされていること() {
        val memory = Memory(0, "question", "answer", 0, 1)
        memory.levelDown(0)

        assertThat(memory.level, `is`(0))
        assertThat(memory.count, `is`(0))
    }
}