package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var textToSpeech: TextToSpeech? = null

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

        // Initialize the text to speech
        textToSpeech = TextToSpeech(this@ExerciseActivity, this)

        setupTheRest()
    }

    // show the rest view
    // hide the exercise view
    private fun setupTheRest() {
        speakOut("Take a rest for ${Constant.restTimer}")

        // show the rest view
        binding?.textRest?.visibility = View.VISIBLE
        binding?.frameLayoutRest?.visibility = View.VISIBLE

        // show the next exercise
        binding?.textViewTitleNextExercise?.visibility = View.VISIBLE
        binding?.textViewNextExercise?.visibility = View.VISIBLE
        binding?.textViewNextExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

        // hide the exercise view
        binding?.textViewExerciseName?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.INVISIBLE
        binding?.imageViewExercise?.visibility = View.INVISIBLE

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
            Constant.restTimerMilli,
            1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBarRest?.progress = Constant.restTimer - restProgress
                binding?.textViewRestTimer?.text = (Constant.restTimer - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupTheExercise()
            }
        }.start()
    }

    // hide the rest view
    // show the exercise view
    private fun setupTheExercise() {
        restTimer?.cancel()
        restProgress = 0

        // hide the rest view
        binding?.textRest?.visibility = View.INVISIBLE
        binding?.frameLayoutRest?.visibility = View.INVISIBLE

        // hide the next exercise
        binding?.textViewTitleNextExercise?.visibility = View.INVISIBLE
        binding?.textViewNextExercise?.visibility = View.INVISIBLE

        // show the exercise view
        binding?.textViewExerciseName?.visibility = View.VISIBLE
        binding?.frameLayoutExercise?.visibility = View.VISIBLE
        binding?.imageViewExercise?.visibility = View.VISIBLE

        // text to speech exercise
        speakOut(exerciseList!![currentExercisePosition].getName())

        // set the current exercise
        binding?.imageViewExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.textViewExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        startExercise()
    }

    private fun startExercise() {
        binding?.progressBarExercise?.progress = restProgress
        restTimer = object: CountDownTimer(
            Constant.exerciseMill,
            1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBarExercise?.progress = Constant.exerciseTimer - restProgress
                binding?.textViewExerciseTimer?.text = (Constant.exerciseTimer - restProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList!!.size-1) {
                    setupTheRest()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations, You have completed 7 minutes workout.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    /**
     * Function is used to speak the text what we pass to it.
     */
    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    // reset the text to speech
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

        binding = null
    }

    // init the text to speech
    override fun onInit(status: Int) {
        // check if text to speech is success launched
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech!!.setLanguage(Locale.US)

            // check if language is available
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Sorry, language is not available!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this@ExerciseActivity,
                "Initialization Failed!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}