package com.github.mag0716.memorytraining.model

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by mag0716 on 2017/06/20.
 */
class LevelTest {

    @Test
    fun LEVEL1() {
        assertThat(Level.LEVEL1.id, `is`(0))
        assertThat(Level.LEVEL1.trainingInterval, `is`(30 * 1000L))
        assertThat(Level.LEVEL1.getPreviousLevel(0), `is`(Level.LEVEL1))
        assertThat(Level.LEVEL1.getNextLevel(0), `is`(Level.LEVEL2))
    }

    @Test
    fun LEVEL2() {
        assertThat(Level.LEVEL2.id, `is`(1))
        assertThat(Level.LEVEL2.trainingInterval, `is`(3 * 60 * 1000L))
        assertThat(Level.LEVEL2.getPreviousLevel(0), `is`(Level.LEVEL1))
        assertThat(Level.LEVEL2.getNextLevel(0), `is`(Level.LEVEL3))
    }

    @Test
    fun LEVEL3() {
        assertThat(Level.LEVEL3.id, `is`(2))
        assertThat(Level.LEVEL3.trainingInterval, `is`(1 * 60 * 60 * 1000L))
        assertThat(Level.LEVEL3.getPreviousLevel(0), `is`(Level.LEVEL2))
        assertThat(Level.LEVEL3.getNextLevel(0), `is`(Level.LEVEL4))
    }

    @Test
    fun LEVEL4() {
        assertThat(Level.LEVEL4.id, `is`(3))
        assertThat(Level.LEVEL4.trainingInterval, `is`(1 * 24 * 60 * 60 * 1000L))
        assertThat(Level.LEVEL4.getPreviousLevel(0), `is`(Level.LEVEL3))
        assertThat(Level.LEVEL4.getNextLevel(0), `is`(Level.LEVEL5))
    }

    @Test
    fun LEVEL5() {
        assertThat(Level.LEVEL5.id, `is`(4))
        assertThat(Level.LEVEL5.trainingInterval, `is`(30 * 24 * 60 * 60 * 1000L))
        assertThat(Level.LEVEL5.getPreviousLevel(0), `is`(Level.LEVEL4))
        assertThat(Level.LEVEL5.getNextLevel(0), `is`(Level.LEVEL5))
    }
}