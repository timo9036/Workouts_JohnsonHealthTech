package com.example.workouts_johnsonhealthtech.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.ui.components.DifficultySelector
import com.example.workouts_johnsonhealthtech.ui.components.SectionHeader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    viewModel: WorkoutDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.workoutState.collectAsState()

    var editableWorkout by remember { mutableStateOf<Workout?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is WorkoutDetailUiState.Success) {
            editableWorkout = (uiState as WorkoutDetailUiState.Success).workout
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Workout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editableWorkout?.let {
                        viewModel.updateWorkout(it)
                        onBack()
                    }
                }
            ) {
                Icon(Icons.Filled.Save, contentDescription = "Save Workout")
            }
        }
    ) { padding ->
        when (uiState) {
            is WorkoutDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is WorkoutDetailUiState.Success -> {
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
                            value = editableWorkout?.name ?: "",
                            onValueChange = { editableWorkout = editableWorkout?.copy(name = it) },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null) },
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editableWorkout?.duration?.toString() ?: "",
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    editableWorkout = editableWorkout?.copy(duration = newValue.toIntOrNull() ?: 0)
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
                            value = editableWorkout?.equipment ?: "",
                            onValueChange = { editableWorkout = editableWorkout?.copy(equipment = it) },
                            label = { Text("Equipment (e.g., Dumbbells)") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Filled.FitnessCenter, contentDescription = null) },
                            singleLine = true
                        )
                    }

                    item {
                        DifficultySelector(
                            selectedDifficulty = editableWorkout?.difficulty,
                            onDifficultySelected = {
                                editableWorkout = editableWorkout?.copy(difficulty = it)
                            }
                        )
                    }
                }
            }
            is WorkoutDetailUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = (uiState as WorkoutDetailUiState.Error).message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

