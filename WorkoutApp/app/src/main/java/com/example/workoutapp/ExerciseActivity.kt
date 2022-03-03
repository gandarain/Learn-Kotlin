package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseAdapterStatus? = null

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

        // setup the exercise recycler view
        setupExerciseStatusRecyclerView()
    }

    /**
     * Setup the recycler view
     * Set the layout manager to be horizontal
     * Set the exercise adapter value to be exercise list
     */
    private fun setupExerciseStatusRecyclerView() {
        binding?.recyclerViewStatus?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        exerciseAdapter = ExerciseAdapterStatus(exerciseList!!)
        binding?.recyclerViewStatus?.adapter = exerciseAdapter
    }

    // play the media player
    // show the rest view
    // hide the exercise view
    private fun setupTheRest() {
        playMediaPlayer()

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

    /**
     * Every 1000 ms (1s) increasing the rest progress and change the text view and circular progress
     * Increase the rest progress on tick
     * Increase the current exercise position on finish
     * Set the isSelected to true on finish
     * notifyDataSetChanged to trigger the changes of exercise adapter
     */
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
                // set the isSelected to true on finish
                exerciseList!![currentExercisePosition].setIsSelected(true)
                // notifyDataSetChanged to trigger the changes of exercise adapter
                exerciseAdapter!!.notifyDataSetChanged()
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

    /**
     * Every 1000 ms (1s) increasing the exercise progress and change the text view and circular progress
     * Increase the exercise progress on tick
     * Set the isSelected to false, isCompleted to true on finish
     * notifyDataSetChanged to trigger the changes of exercise adapter
     */
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
                // set the isSelected to false on finish
                exerciseList!![currentExercisePosition].setIsSelected(false)
                // set the isCompleted to false on finish
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                // notifyDataSetChanged to trigger the changes of exercise adapter
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList!!.size-1) {
                    setupTheRest()
                } else {
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
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

    /**
     * Here the sound file is added in to "raw" folder in resources.
     * And played using MediaPlayer. MediaPlayer class can be used to control playback
     * of audio/video files and streams.
     */
    private fun playMediaPlayer() {
        try {
            val soundURI =
                Uri.parse("android.resource://com.example.workoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            // Sets the player to be looping or non-looping.
            player?.isLooping = false
            // Starts Playback.
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // reset the value of restTimer and binding when destroy the exercise activity
    // reset the text to speech
    // reset the player
    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
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