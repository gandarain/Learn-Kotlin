package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()

        setupButtonValidate()
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

    /**
     * Setup the validate button
     * Check if the input is not empty
     * Call calculateBmi if the input is not empty
     * Call displayBmiResult
     */
    private fun setupButtonValidate() {
        binding?.buttonCalculateBmi?.setOnClickListener {
            if (validateMetricUnit()) {
                val bmiResult = calculateBmi()
                displayBmiResult(bmiResult)
            } else {
                Toast.makeText(
                    this@BmiActivity,
                    "Please fill the height and weight!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * validateMetricUnit
     */
    private fun validateMetricUnit(): Boolean {
        var isValid = true

        if (binding?.editTextWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.editTextHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    /**
     * Calculate BMI
     */
    private fun calculateBmi(): Float {
        val heightValue: Float = binding?.editTextHeight?.text.toString().toFloat() / Constant.centiToMeter
        val weightValue: Float = binding?.editTextWeight?.text.toString().toFloat()

        return weightValue / (heightValue * heightValue)
    }

    /**
     * Display BMI Result
     */
    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15F) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.textViewBmiValue?.text = bmiValue
        binding?.textViewBmiType?.text = bmiLabel
        binding?.textViewBmiDescription?.text = bmiDescription
        binding?.linerLayoutBmi?.visibility = View.VISIBLE
    }
}