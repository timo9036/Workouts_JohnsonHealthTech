package com.example.workouts_johnsonhealthtech.data

import com.example.workouts_johnsonhealthtech.data.local.WorkoutDao
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepositoryImpl
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WorkoutRepositoryImplTest {

    private lateinit var workoutDao: WorkoutDao
    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        workoutDao = mockk(relaxed = true)
        repository = WorkoutRepositoryImpl(workoutDao)
    }

    @Test
    fun getWorkouts_returns_flow_from_dao() = runBlocking {
        val workouts = listOf(Workout("1", "Morning Stretch", null, 15, Difficulty.BEGINNER))
        every { workoutDao.getAllWorkouts() } returns flowOf(workouts)

        val result = repository.getWorkouts().first()

        assertEquals(workouts, result)
    }

    @Test
    fun getWorkoutById_returns_flow_from_dao() = runBlocking {
        val workout = Workout("1", "Chest Day", "Bench", 60, Difficulty.INTERMEDIATE)
        every { workoutDao.getWorkoutById("1") } returns flowOf(workout)

        val result = repository.getWorkoutById("1").first()

        assertEquals(workout, result)
    }

    @Test
    fun updateWorkout_calls_dao() = runBlocking {
        val workout = Workout("1", "Leg Day", "Squat Rack", 75, Difficulty.ADVANCED)

        repository.updateWorkout(workout)

        coVerify { workoutDao.updateWorkout(workout) }
    }
}