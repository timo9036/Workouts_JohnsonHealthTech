package com.example.workouts_johnsonhealthtech.di

import android.app.Application
import androidx.room.Room
import com.example.workouts_johnsonhealthtech.data.local.AppDatabase
import com.example.workouts_johnsonhealthtech.data.local.DatabaseCallback
import com.example.workouts_johnsonhealthtech.data.local.WorkoutDao
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepository
import com.example.workouts_johnsonhealthtech.data.repository.WorkoutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application, workoutDao: Provider<WorkoutDao>, applicationScope: CoroutineScope): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "workout_db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(DatabaseCallback(app, workoutDao, applicationScope))
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(db: AppDatabase): WorkoutDao = db.workoutDao()

    @Provides
    @Singleton
    fun provideWorkoutRepository(dao: WorkoutDao): WorkoutRepository {
        return WorkoutRepositoryImpl(dao)
    }
}