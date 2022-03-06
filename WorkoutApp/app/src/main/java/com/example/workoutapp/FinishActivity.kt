package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()

        buttonFinishListener()

        // setup exercise history dao
        val exerciseHistoryDao = (application as ExerciseHistoryApp).db.exerciseHistoryDao()
        addExerciseHistoryRecord(exerciseHistoryDao)
    }

    /**
     * Setup the toolbar
     */
    private fun setupToolbar() {
        // set the toolbar
        setSupportActionBar(binding?.toolBarEnd)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "FINISH EXERCISE"
        }
        // on press back
        binding?.toolBarEnd?.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * Setup the finish button listener
     * Go Back to main activity
     */
    private fun buttonFinishListener() {
        binding?.buttonFinish?.setOnClickListener {
            finish()
        }
    }

    /**
     * Method is used to show the Generate Date.
     */
    private fun generateDate(): String {
        val c = Calendar.getInstance() // Calendars Current Instance
        val dateTime = c.time // Current Date and Time of the system.

        /**
         * Here we have taken an instance of Date Formatter as it will format our
         * selected date in the format which we pass it as an parameter and Locale.
         * Here I have passed the format as dd MMM yyyy HH:mm:ss.
         *
         * The Locale : Gets the current value of the default locale for this instance
         * of the Java Virtual Machine.
         */
        // Date Formatter
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        // dateTime is formatted in the given format.
        return sdf.format(dateTime)
    }

    // launch a coroutine block to call the method for inserting entry
    private fun addExerciseHistoryRecord(exerciseHistoryDao: ExerciseHistoryDao) {
        val date = generateDate()
        lifecycleScope.launch {
            exerciseHistoryDao.insert(ExerciseHistoryEntity(date))
            Log.e(
                "Date : ",
                "Added..."
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}