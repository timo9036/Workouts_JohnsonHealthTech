package com.example.workouts_johnsonhealthtech.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workouts_johnsonhealthtech.ui.UiState
import com.example.workouts_johnsonhealthtech.ui.components.DifficultySelector
import com.example.workouts_johnsonhealthtech.ui.components.SectionHeader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    viewModel: WorkoutDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.workoutState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val workout = state.data
            var name by rememberSaveable(workout) { mutableStateOf(workout.name) }
            var duration by rememberSaveable(workout) { mutableStateOf(workout.duration.toString()) }
            var equipment by rememberSaveable(workout) { mutableStateOf(workout.equipment ?: "") }
            var difficulty by rememberSaveable(workout) { mutableStateOf(workout.difficulty) }

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Edit Workout") },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            val updatedWorkout = workout.copy(
                                name = name,
                                duration = duration.toIntOrNull() ?: 0,
                                equipment = equipment.takeIf { it.isNotBlank() },
                                difficulty = difficulty
                            )
                            viewModel.updateWorkout(updatedWorkout)
                            onBack()
                        }
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = "Save Workout")
                    }
                }
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
                ) {
                    item {
                        SectionHeader("Details")
                    }
                    item {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null) },
                            singleLine = true
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = duration,
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    duration = newValue
                                }
                            },
                            label = { Text("Duration (mins)") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Filled.Timer, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = equipment,
                            onValueChange = { equipment = it },
                            label = { Text("Equipment (e.g., Dumbbells)") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.FitnessCenter,
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )
                    }
                    item {
                        DifficultySelector(
                            selectedDifficulty = difficulty,
                            onDifficultySelected = { newDifficulty ->
                                difficulty = newDifficulty
                            }
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

