package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise-history-table")
data class ExerciseHistoryEntity(
    @PrimaryKey()
    val date: String = ""
)