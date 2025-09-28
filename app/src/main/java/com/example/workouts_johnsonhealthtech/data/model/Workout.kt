package com.example.workouts_johnsonhealthtech.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "workouts")
data class Workout(
    @PrimaryKey val id: String,
    val name: String,
    val equipment: String? = null,
    val duration: Int,
    val difficulty: Difficulty? = null
)