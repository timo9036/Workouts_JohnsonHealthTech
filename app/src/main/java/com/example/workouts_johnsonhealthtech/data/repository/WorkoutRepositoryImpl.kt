package com.example.workouts_johnsonhealthtech.data.repository

import com.example.workouts_johnsonhealthtech.data.local.WorkoutDao
import com.example.workouts_johnsonhealthtech.data.model.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {
    override fun getWorkouts(): Flow<List<Workout>> = workoutDao.getAllWorkouts()

    override fun getWorkoutById(id: String): Flow<Workout> = workoutDao.getWorkoutById(id)

    override suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }
}