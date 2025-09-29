package com.example.workouts_johnsonhealthtech.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouts_johnsonhealthtech.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_workoutListScreen_displaysTitle() {
        composeTestRule.onNodeWithText("Workouts").assertIsDisplayed()
    }

    @Test
    fun test_workoutListScreen_displaysWorkoutList() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Cardio Workout")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Cardio Workout").assertIsDisplayed()
    }

    @Test
    fun test_workoutListScreen_navigateToDetail() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Cardio Workout")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Cardio Workout").performClick()

        composeTestRule.onNodeWithText("Edit Workout").assertIsDisplayed()
    }
}