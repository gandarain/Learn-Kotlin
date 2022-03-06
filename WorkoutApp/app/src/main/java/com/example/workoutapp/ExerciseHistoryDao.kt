package com.example.workoutapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Todo 1: create the dao interface
@Dao
interface ExerciseHistoryDao {
    // Todo 2: create a suspend insert function for saving an entry
    @Insert
    suspend fun insert(exerciseHistory: ExerciseHistoryEntity)

    // Todo 3: create a function to read all exercise history, this returns a Flow
    @Query("SELECT * FROM `exercise-history-table`")
    // flow is used to hold a value that can change at runtime
    fun fetchAllExerciseHistory(): Flow<List<ExerciseHistoryEntity>>
}