package com.example.workouts_johnsonhealthtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.workouts_johnsonhealthtech.ui.theme.Workouts_JohnsonHealthTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Workouts_JohnsonHealthTechTheme {

            }
        }
    }
}


