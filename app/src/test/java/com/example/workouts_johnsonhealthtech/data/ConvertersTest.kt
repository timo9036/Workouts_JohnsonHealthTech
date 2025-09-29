package com.example.workouts_johnsonhealthtech.data

import com.example.workouts_johnsonhealthtech.data.local.Converters
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun fromDifficulty_converts_correctly() {
        assertEquals("BEGINNER", converters.fromDifficulty(Difficulty.BEGINNER))
        assertEquals("INTERMEDIATE", converters.fromDifficulty(Difficulty.INTERMEDIATE))
        assertEquals("ADVANCED", converters.fromDifficulty(Difficulty.ADVANCED))
        assertEquals(null, converters.fromDifficulty(null))
    }

    @Test
    fun toDifficulty_converts_correctly() {
        assertEquals(Difficulty.BEGINNER, converters.toDifficulty("BEGINNER"))
        assertEquals(Difficulty.INTERMEDIATE, converters.toDifficulty("INTERMEDIATE"))
        assertEquals(Difficulty.ADVANCED, converters.toDifficulty("ADVANCED"))
        assertEquals(null, converters.toDifficulty(null))
    }
}