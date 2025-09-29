package com.example.workouts_johnsonhealthtech.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import com.example.workouts_johnsonhealthtech.ui.UiState
import com.example.workouts_johnsonhealthtech.ui.workouts.WorkoutListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WorkoutListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WorkoutListViewModel
    private val mockRepository: WorkoutRepository = mockk()

    @Test
    fun `init - when repository returns workouts - state is Success`() = runTest {
        // Given
        val mockWorkouts = listOf(
            Workout("1", "Morning Stretch", "None", 15, Difficulty.BEGINNER),
            Workout("2", "HIIT Cardio", "None", 30, Difficulty.INTERMEDIATE)
        )
        coEvery { mockRepository.getWorkouts() } returns flowOf(mockWorkouts)

        // When
        viewModel = WorkoutListViewModel(mockRepository)

        // Then
        val uiState = viewModel.workoutsState.value
        assertTrue(uiState is UiState.Success)
        assertEquals(mockWorkouts, (uiState as UiState.Success).data)
    }

    @Test
    fun `init - when repository throws exception - state is Error`() = runTest {
        // Given
        val errorMessage = "Database error"
        coEvery { mockRepository.getWorkouts() } returns flow { throw RuntimeException(errorMessage) }

        // When
        viewModel = WorkoutListViewModel(mockRepository)

        // Then
        val uiState = viewModel.workoutsState.value
        assertTrue(uiState is UiState.Error)
        assertEquals(errorMessage, (uiState as UiState.Error).message)
    }
}