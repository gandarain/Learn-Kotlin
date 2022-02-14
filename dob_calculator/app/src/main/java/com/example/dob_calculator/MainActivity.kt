package com.example.dob_calculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // set private so textViewSelectedDate only can access in here / only on this class
    private var textViewSelectedDate: TextView? = null
    private var textViewAgeInMinute: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // define id text view and button
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        textViewSelectedDate = findViewById(R.id.selectedDate)
        textViewAgeInMinute = findViewById(R.id.ageInMinute)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // date picker configuration
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // showing toast
                Toast.makeText(
                    this, "Year $selectedYear, Month ${selectedMonth+1}, Day $selectedDayOfMonth", Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedYear/${selectedMonth+1}/$selectedDayOfMonth"
                textViewSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    // time === getTime()
                    val selectedDateInMinutes = theDate.time / 6000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 6000
                        val differentInMinutes = currentDateInMinutes - selectedDateInMinutes
                        textViewAgeInMinute?.text = differentInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}