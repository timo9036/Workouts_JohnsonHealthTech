package com.example.workouts_johnsonhealthtech.ui.detail

import com.example.workouts_johnsonhealthtech.data.model.Workout

sealed class WorkoutDetailUiState {
    object Loading : WorkoutDetailUiState()
    data class Success(val workout: Workout) : WorkoutDetailUiState()
    data class Error(val message: String) : WorkoutDetailUiState()
}