package com.example.workouts_johnsonhealthtech.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.ui.components.DifficultySelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DifficultySelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun difficultySelector_initialState_isCorrect() {
        composeTestRule.setContent {
            var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
            DifficultySelector(
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = { selectedDifficulty = it }
            )
        }

        composeTestRule.onNodeWithText("Beginner").assertExists()
        composeTestRule.onNodeWithText("Intermediate").assertExists()
        composeTestRule.onNodeWithText("Advanced").assertExists()
    }

    @Test
    fun selectDifficulty_updatesStateAndUi() {
        composeTestRule.setContent {
            var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
            DifficultySelector(
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = { selectedDifficulty = it }
            )
        }

        composeTestRule.onNodeWithText("Intermediate").performClick()
        composeTestRule.onNodeWithText("Intermediate").assertIsSelected()
    }

    @Test
    fun deselectDifficulty_updatesStateAndUi() {
        composeTestRule.setContent {
            var selectedDifficulty by remember { mutableStateOf<Difficulty?>(Difficulty.ADVANCED) }
            DifficultySelector(
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = { selectedDifficulty = it }
            )
        }

        composeTestRule.onNodeWithText("Advanced").performClick()
        composeTestRule.onNodeWithText("Advanced").assertIsNotSelected()
    }
}