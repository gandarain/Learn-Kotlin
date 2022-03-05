package com.example.roomdemo

import android.app.Application
// Todo : create the application class and initialize the database
class EmployeeApp: Application() {
    val db by lazy {
        EmployeeDatabase.getInstance(this)
    }
}