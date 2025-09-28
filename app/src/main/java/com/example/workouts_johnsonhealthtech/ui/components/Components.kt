package com.example.workouts_johnsonhealthtech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workouts_johnsonhealthtech.data.model.Difficulty


@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultySelector(
    selectedDifficulty: Difficulty?,
    onDifficultySelected: (Difficulty?) -> Unit
) {
    val difficulties = Difficulty.values().toList()

    Text("Difficulty", style = MaterialTheme.typography.bodyLarge)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        difficulties.forEach { difficulty ->
            FilterChip(
                selected = selectedDifficulty == difficulty,
                onClick = {
                    val newSelection = if (selectedDifficulty == difficulty) null else difficulty
                    onDifficultySelected(newSelection)
                },
                label = { Text(difficulty.displayName) },
                leadingIcon = if (selectedDifficulty == difficulty) {
                    { Icon(Icons.Filled.Done, contentDescription = "Selected") }
                } else {
                    null
                }
            )
        }
    }
}