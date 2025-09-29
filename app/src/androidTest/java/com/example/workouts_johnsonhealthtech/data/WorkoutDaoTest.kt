package com.example.workouts_johnsonhealthtech.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouts_johnsonhealthtech.data.local.AppDatabase
import com.example.workouts_johnsonhealthtech.data.local.WorkoutDao
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.data.model.Workout
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest {

    private lateinit var workoutDao: WorkoutDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        workoutDao = db.workoutDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWorkout() = runBlocking {
        val workout = Workout("1", "Test Workout", "Dumbbells", 30, Difficulty.INTERMEDIATE)
        workoutDao.insertAll(listOf(workout))
        val byId = workoutDao.getWorkoutById("1").first()
        Assert.assertEquals(byId, workout)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWorkouts() = runBlocking {
        val workout1 = Workout("1", "Test 1", null, 20, Difficulty.BEGINNER)
        val workout2 = Workout("2", "Test 2", "Barbell", 45, Difficulty.ADVANCED)
        workoutDao.insertAll(listOf(workout1, workout2))
        val allWorkouts = workoutDao.getAllWorkouts().first()
        Assert.assertEquals(allWorkouts.size, 2)
        Assert.assertEquals(allWorkouts[0], workout1)
    }

    @Test
    @Throws(Exception::class)
    fun updateWorkout() = runBlocking {
        val workout = Workout("1", "Original Name", "None", 10, Difficulty.BEGINNER)
        workoutDao.insertAll(listOf(workout))

        val updatedWorkout = workout.copy(name = "Updated Name")
        workoutDao.updateWorkout(updatedWorkout)

        val retrieved = workoutDao.getWorkoutById("1").first()
        Assert.assertEquals(retrieved.name, "Updated Name")
    }
}