package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // implementation view binding
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_main)
        setContentView(binding?.root)

        // val buttonStart: FrameLayout = findViewById(R.id.buttonStart)
        binding?.buttonStart?.setOnClickListener {
            // navigate to ExerciseActivity
            val intent = Intent(this@MainActivity, ExerciseActivity::class.java)
            startActivity(intent)
        }

        bmiButtonListener()

        historyButtonListener()
    }

    // destroy the binding to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /**
     * bmiButtonListener
     * navigate to BmiActivity
     */
    private fun bmiButtonListener() {
        binding?.buttonBmi?.setOnClickListener {
            // navigate to BmiActivity
            val intent = Intent(this@MainActivity, BmiActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * historyButtonListener
     * navigate to HistoryActivity
     */
    private fun historyButtonListener() {
        binding?.buttonHistory?.setOnClickListener {
            // navigate to HistoryActivity
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}