package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private var textViewResultUsername: TextView? = null
    private var textViewResultScore: TextView? = null
    private var buttonFinish: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        this.textViewResultUsername = findViewById(R.id.textViewResultUsername)
        this.textViewResultScore = findViewById(R.id.textViewResultScore)
        this.buttonFinish = findViewById(R.id.buttonFinish)

        this.textViewResultUsername?.text = intent.getStringExtra(Constants.USER_NAME)

        val correctAnswer = intent.getIntExtra(Constants.CORRECT_ANSWER, 0)
        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        this.textViewResultScore?.text = "Your score is ${correctAnswer} of ${totalQuestion}"

        buttonFinish?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}