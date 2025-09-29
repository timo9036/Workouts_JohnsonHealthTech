package com.example.workouts_johnsonhealthtech.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object WorkoutList : Screen("workoutList")
    object WorkoutDetail : Screen("workoutDetail/{workoutId}") {
        fun createRoute(workoutId: String) = "workoutDetail/$workoutId"
    }
}