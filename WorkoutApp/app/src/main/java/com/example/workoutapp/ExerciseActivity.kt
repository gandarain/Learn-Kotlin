package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var constRestTimer: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // set the toolbar
        setSupportActionBar(binding?.toolbarExercise)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // on press back
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupTheRestExercise()
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    // setup the rest progress bar
    private fun setupTheRestExercise() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    // Every 1000 ms (1s) increasing the rest progress and change the text view and circular progress
    private fun setRestProgressBar() {
        binding?.progressBarExercise?.progress = restProgress
        restTimer = object: CountDownTimer(
            10000,
            1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBarExercise?.progress = constRestTimer - restProgress
                binding?.textViewTimer?.text = (constRestTimer - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }
}