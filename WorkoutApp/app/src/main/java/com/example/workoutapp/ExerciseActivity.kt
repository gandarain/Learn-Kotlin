package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var constRestTimer: Int = 10
    private var constExerciseTimer: Int = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setup the exercise list
        exerciseList = Constant.defaultExerciseList()

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

        setupTheRest()
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    // setup the rest progress bar
    private fun setupTheRest() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    // Every 1000 ms (1s) increasing the rest progress and change the text view and circular progress
    private fun setRestProgressBar() {
        binding?.progressBarRest?.progress = restProgress
        restTimer = object: CountDownTimer(
            10000,
            1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBarRest?.progress = constRestTimer - restProgress
                binding?.textViewRestTimer?.text = (constRestTimer - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupTheExercise()
            }
        }.start()
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    // setup the rest progress bar
    private fun setupTheExercise() {
        restTimer?.cancel()
        restProgress = 0
        binding?.frameLayoutRest?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.VISIBLE
        startExercise()
    }

    private fun startExercise() {
        binding?.progressBarExercise?.progress = restProgress
        restTimer = object: CountDownTimer(
            30000,
            1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBarExercise?.progress = constExerciseTimer - restProgress
                binding?.textViewExerciseTimer?.text = (constExerciseTimer - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Finish exercise one",
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