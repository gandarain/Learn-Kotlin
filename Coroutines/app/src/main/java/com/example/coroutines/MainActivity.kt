package com.example.coroutines

import android.app.Dialog
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
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val executeButton: Button = findViewById(R.id.executeButton)
        executeButton.setOnClickListener {
            // launch coroutine function in life cycle scope
            lifecycleScope.launch {
                showDialog()
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
                hideDialog()
                Toast.makeText(
                    this@MainActivity,
                    "Done",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showDialog() {
        // Define the dialog
        dialog = Dialog(this@MainActivity)

        /* Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen. */
        dialog?.setContentView(R.layout.dialog_custom_progress)
        dialog?.setCancelable(false)

        // Start the dialog and display it on screen.
        dialog?.show()
    }

    /**
     * Method is used to hide the Custom Progress Dialog.
     */
    private fun hideDialog() {
        // check if dialog null or not
        if (dialog != null) {
            // dismiss dialog when dialog is not null
            dialog?.dismiss()
            // set dialog to null
            dialog = null
        }
    }
}