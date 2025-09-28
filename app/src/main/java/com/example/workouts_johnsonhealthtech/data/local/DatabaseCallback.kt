package com.example.workouts_johnsonhealthtech.data.local

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class DatabaseCallback(
    private val context: Context,
    private val workoutDao: Provider<WorkoutDao>,
    private val applicationScope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch {
            val workouts = InitialData.getWorkouts(context)
            workoutDao.get().insertAll(workouts)
        }
    }
}