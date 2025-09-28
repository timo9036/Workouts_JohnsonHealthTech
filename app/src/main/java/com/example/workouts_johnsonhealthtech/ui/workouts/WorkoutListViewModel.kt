package com.example.workouts_johnsonhealthtech.ui.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(
    repository: WorkoutRepository
) : ViewModel() {

    val workouts: StateFlow<List<Workout>> = repository.getWorkouts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}