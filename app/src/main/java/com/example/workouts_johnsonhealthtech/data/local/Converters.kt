package com.example.workouts_johnsonhealthtech.data.local

import androidx.room.TypeConverter
import com.example.workouts_johnsonhealthtech.data.model.Difficulty

class Converters {
    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty?): String?{
        return difficulty?.name
    }

    @TypeConverter
    fun toDifficulty(value: String?): Difficulty?{
        return value?.let { enumValueOf<Difficulty>(it) }
    }
}