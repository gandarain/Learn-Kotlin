package com.example.speechtext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.speechtext.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initialize the text to speech
        textToSpeech = TextToSpeech(this, this)

        // set on click listener for button
        binding?.buttonSpeech?.setOnClickListener {
            if (binding?.editTextSpeech?.text!!.isNotEmpty()) {
                speakOut(binding?.editTextSpeech?.text.toString())
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Please input the text!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onInit(status: Int) {
        // check if text to speech is success launched
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech!!.setLanguage(Locale.US)

            // check if language is available
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
                Toast.makeText(
                    this@MainActivity,
                    "Sorry, language is not available!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
            Toast.makeText(
                this@MainActivity,
                "Initialization Failed!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Here is Destroy function we will stop and shutdown the tts which is initialized above.
     */
    public override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

        super.onDestroy()
    }

    /**
     * Function is used to speak the text what we pass to it.
     */
    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        Log.e("TTS", "speakOut function")
    }
}