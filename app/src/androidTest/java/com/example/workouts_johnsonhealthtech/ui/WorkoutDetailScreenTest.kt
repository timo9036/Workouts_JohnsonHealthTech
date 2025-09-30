package com.example.workouts_johnsonhealthtech.ui

import android.content.pm.ActivityInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouts_johnsonhealthtech.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun navigateToDetailScreen() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Cardio Workout")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Cardio Workout").performClick()
    }

    @Test
    fun detailScreen_displaysCorrectly() {
        composeTestRule.onNodeWithText("Edit Workout").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Duration (mins)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Equipment (e.g., Dumbbells)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Difficulty").assertIsDisplayed()
    }

    @Test
    fun editWorkoutName_andSaveChanges() {
        val newWorkoutName = "Updated Cardio"

        val nameTextField = composeTestRule.onNode(
            hasText("Name"),
            useUnmergedTree = true
        ).onParent()

        nameTextField.performTextClearance()
        nameTextField.performTextInput(newWorkoutName)

        composeTestRule.onNodeWithContentDescription("Save Workout").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText(newWorkoutName)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText(newWorkoutName).assertIsDisplayed()
    }

    @Test
    fun enterInvalidDuration_isHandledGracefully() {
        val durationTextField = composeTestRule.onNode(
            hasText("Duration (mins)"),
            useUnmergedTree = true
        ).onParent()

        durationTextField.performTextInput("abc")

        durationTextField.assert(hasText("30"))

        durationTextField.performTextClearance()
        durationTextField.performTextInput("abc")

        durationTextField.assert(hasText(""))
    }

    @Test
    fun stateIsPreserved_onScreenRotation() {
        val testName = "Rotation Test"
        val testDuration = "123"

        val nameTextField = composeTestRule.onNode(hasText("Name"), useUnmergedTree = true).onParent()
        val durationTextField = composeTestRule.onNode(hasText("Duration (mins)"), useUnmergedTree = true).onParent()

        nameTextField.performTextClearance()
        nameTextField.performTextInput(testName)
        durationTextField.performTextClearance()
        durationTextField.performTextInput(testDuration)

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(testName).assertIsDisplayed()
        composeTestRule.onNodeWithText(testDuration).assertIsDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(testName).assertIsDisplayed()
        composeTestRule.onNodeWithText(testDuration).assertIsDisplayed()
    }
}