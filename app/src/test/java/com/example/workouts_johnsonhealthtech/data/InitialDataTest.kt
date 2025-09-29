package com.example.workouts_johnsonhealthtech.data

import android.content.Context
import android.content.res.AssetManager
import com.example.workouts_johnsonhealthtech.data.local.InitialData
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class InitialDataTest {

    private val mockContext = mockk<Context>()
    private val mockAssetManager = mockk<AssetManager>()

    @Before
    fun setUp() {
        every { mockContext.assets } returns mockAssetManager
    }

    @Test
    fun `getWorkouts - parses valid json correctly`() {
        val validJson = """
            [
              {
                "name": "Cardio Workout",
                "id": "589de45cc3299e1600b2f57d",
                "equipment": "Treadmill",
                "duration": 30,
                "difficulty": "Intermediate"
              }
            ]
        """.trimIndent()
        val inputStream = ByteArrayInputStream(validJson.toByteArray())
        every { mockAssetManager.open("workouts.json") } returns inputStream

        val workouts = InitialData.getWorkouts(mockContext)

        assertEquals(1, workouts.size)
        val workout = workouts[0]
        assertEquals("Cardio Workout", workout.name)
        assertEquals(30, workout.duration)
        assertEquals(Difficulty.INTERMEDIATE, workout.difficulty)
    }

    @Test
    fun `getWorkouts - handles various difficulty formats`() {
        val jsonWithVariousDifficulties = """
            [
              { "name": "Beginner Test", "id": "1", "duration": 10, "difficulty": "BEGINNER" },
              { "name": "Intermediate Test", "id": "2", "duration": 20, "difficulty": "intermediate" },
              { "name": "Advanced Test", "id": "3", "duration": 30, "difficulty": "Advanced" },
              { "name": "Invalid Test", "id": "4", "duration": 40, "difficulty": "expert" },
              { "name": "Null Test", "id": "5", "duration": 50, "difficulty": null },
              { "name": "Missing Test", "id": "6", "duration": 60 }
            ]
        """.trimIndent()
        val inputStream = ByteArrayInputStream(jsonWithVariousDifficulties.toByteArray())
        every { mockAssetManager.open("workouts.json") } returns inputStream

        val workouts = InitialData.getWorkouts(mockContext)

        assertEquals(6, workouts.size)
        assertEquals(Difficulty.BEGINNER, workouts[0].difficulty)
        assertEquals(Difficulty.INTERMEDIATE, workouts[1].difficulty)
        assertEquals(Difficulty.ADVANCED, workouts[2].difficulty)
        assertNull("Invalid difficulty string should result in null", workouts[3].difficulty)
        assertNull("Explicit null difficulty should be null", workouts[4].difficulty)
        assertNull("Missing difficulty field should result in null", workouts[5].difficulty)
    }

    @Test
    fun `getWorkouts - handles empty json array`() {
        val emptyJson = "[]"
        val inputStream = ByteArrayInputStream(emptyJson.toByteArray())
        every { mockAssetManager.open("workouts.json") } returns inputStream

        val workouts = InitialData.getWorkouts(mockContext)

        assertNotNull(workouts)
        assertEquals(0, workouts.size)
    }
}