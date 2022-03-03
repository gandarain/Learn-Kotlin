package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()
    }

    /**
     * Setup the toolbar
     */
    private fun setupToolbar() {
        // set the toolbar
        setSupportActionBar(binding?.toolBarBmi)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        // on press back
        binding?.toolBarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}