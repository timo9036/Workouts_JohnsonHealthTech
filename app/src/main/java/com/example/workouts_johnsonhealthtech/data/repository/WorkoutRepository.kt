package com.example.workouts_johnsonhealthtech.data.repository

import com.example.workouts_johnsonhealthtech.data.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getWorkouts(): Flow<List<Workout>>
    fun getWorkoutById(id: String): Flow<Workout>
    suspend fun updateWorkout(workout: Workout)
}