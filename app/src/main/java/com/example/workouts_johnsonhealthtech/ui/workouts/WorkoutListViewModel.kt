package com.example.workouts_johnsonhealthtech.ui.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import com.example.workouts_johnsonhealthtech.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(
    repository: WorkoutRepository
) : ViewModel() {

    private val _workoutsState = MutableStateFlow<UiState<List<Workout>>>(UiState.Loading)
    val workoutsState: StateFlow<UiState<List<Workout>>> = _workoutsState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWorkouts()
                .catch { exception ->
                    _workoutsState.value = UiState.Error(exception.message ?: "An unknown error occurred")
                }
                .collect { workouts ->
                    _workoutsState.value = UiState.Success(workouts)
                }
        }
    }
}