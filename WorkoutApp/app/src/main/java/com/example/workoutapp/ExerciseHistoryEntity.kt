package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Create an entity with @param [history-table]
 * Use @param [date] as primary key
 * */
@Entity(tableName = "history-table")
data class ExerciseHistoryEntity(
    @PrimaryKey
    val date: String
)