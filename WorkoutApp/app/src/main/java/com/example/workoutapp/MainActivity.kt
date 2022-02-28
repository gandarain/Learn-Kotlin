package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart: FrameLayout = findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Here will be start the exercise",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}