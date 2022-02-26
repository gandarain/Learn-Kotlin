package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val executeButton: Button = findViewById(R.id.executeButton)
        executeButton.setOnClickListener {
            // launch coroutine function in life cycle scope
            lifecycleScope.launch {
                execute()
            }
        }
    }

    // execute is coroutine function
    // add suspend key to create a coroutine function
    private suspend fun execute() {
        // run loop inside coroutine
        // move the task into different thread (background thread)
        withContext(Dispatchers.IO) {
            for (i in 1..10000) {
                Log.e("delay", "" + i)
            }

            // run related ui on ui thread
            runOnUiThread {
                Toast.makeText(
                    this@MainActivity,
                    "Done",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}