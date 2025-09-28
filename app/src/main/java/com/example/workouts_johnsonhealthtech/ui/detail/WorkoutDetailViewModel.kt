package com.example.workouts_johnsonhealthtech.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val workoutId: String = savedStateHandle.get<String>("workoutId")!!

    private val _workoutState = MutableStateFlow<WorkoutDetailUiState>(WorkoutDetailUiState.Loading)
    val workoutState: StateFlow<WorkoutDetailUiState> = _workoutState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId)
                .catch { exception ->
                    _workoutState.value = WorkoutDetailUiState.Error(exception.message ?: "An error occurred")
                }
                .collect { workout ->
                    _workoutState.value = WorkoutDetailUiState.Success(workout)
                }
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.updateWorkout(workout)
        }
    }
}