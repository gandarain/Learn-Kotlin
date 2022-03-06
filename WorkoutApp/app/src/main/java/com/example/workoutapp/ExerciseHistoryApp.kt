package com.example.workoutapp

import android.app.Application
// Todo : create the application class and initialize the database
class ExerciseHistoryApp: Application() {
    val db by lazy {
        ExerciseHistoryDatabase.getInstance(this)
    }
}