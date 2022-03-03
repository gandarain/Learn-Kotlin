package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()

        buttonFinishListener()
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}