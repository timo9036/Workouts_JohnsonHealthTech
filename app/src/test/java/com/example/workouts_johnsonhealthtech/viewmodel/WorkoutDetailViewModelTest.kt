package com.example.workouts_johnsonhealthtech.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import com.example.workouts_johnsonhealthtech.ui.UiState
import com.example.workouts_johnsonhealthtech.ui.detail.WorkoutDetailViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WorkoutDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WorkoutDetailViewModel
    private val mockRepository: WorkoutRepository = mockk(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle

    private val testWorkoutId = "1"
    private val mockWorkout = Workout(testWorkoutId, "Full Body Strength", "Dumbbells", 45, Difficulty.ADVANCED)

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle(mapOf("workoutId" to testWorkoutId))
    }

    @Test
    fun `init - when repository returns a workout - state is Success`() = runTest {
        // Given
        coEvery { mockRepository.getWorkoutById(testWorkoutId) } returns flowOf(mockWorkout)

        // When
        viewModel = WorkoutDetailViewModel(mockRepository, savedStateHandle)

        // Then
        val uiState = viewModel.workoutState.value
        assertTrue(uiState is UiState.Success)
        assertEquals(mockWorkout, (uiState as UiState.Success).data)
    }

    @Test
    fun `init - when repository throws an exception - state is Error`() = runTest {
        // Given
        val errorMessage = "Workout not found"
        coEvery { mockRepository.getWorkoutById(testWorkoutId) } returns flow { throw RuntimeException(errorMessage) }

        // When
        viewModel = WorkoutDetailViewModel(mockRepository, savedStateHandle)

        // Then
        val uiState = viewModel.workoutState.value
        assertTrue(uiState is UiState.Error)
        assertEquals(errorMessage, (uiState as UiState.Error).message)
    }

    @Test
    fun `updateWorkout - calls repository to update the workout`() = runTest {
        // Given
        coEvery { mockRepository.getWorkoutById(testWorkoutId) } returns flowOf(mockWorkout)
        viewModel = WorkoutDetailViewModel(mockRepository, savedStateHandle)
        val updatedWorkout = mockWorkout.copy(name = "Updated Workout Name")

        // When
        viewModel.updateWorkout(updatedWorkout)

        // Then
        coVerify { mockRepository.updateWorkout(updatedWorkout) }
    }
}