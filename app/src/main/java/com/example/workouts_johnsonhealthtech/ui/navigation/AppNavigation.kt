package com.example.workouts_johnsonhealthtech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workouts_johnsonhealthtech.ui.detail.WorkoutDetailScreen
import com.example.workouts_johnsonhealthtech.ui.splash.SplashScreen
import com.example.workouts_johnsonhealthtech.ui.workouts.WorkoutListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(onAnimationFinished = {
                navController.navigate(Screen.WorkoutList.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(Screen.WorkoutList.route) {
            WorkoutListScreen(onWorkoutClick = { workoutId ->
                navController.navigate(Screen.WorkoutDetail.createRoute(workoutId))
            })
        }
        composable(
            route = Screen.WorkoutDetail.route,
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) {
            WorkoutDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}