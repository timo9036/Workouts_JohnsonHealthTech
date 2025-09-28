package com.example.workouts_johnsonhealthtech.ui.navigation

sealed class Screen(val route: String) {
    object WorkoutList : Screen("workoutList")
    object WorkoutDetail : Screen("workoutDetail/{workoutId}") {
        fun createRoute(workoutId: String) = "workoutDetail/$workoutId"
    }
}