package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizQuestionsActivity : AppCompatActivity() {
    private var textViewQuestion: TextView? = null
    private var imageViewFlag: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var textViewProgress: TextView? = null
    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null
    private var buttonSubmit: Button? = null

    private var currentProgress: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        var questionsList: ArrayList<Question> = Constants.getQuestions()
        var question: Question = questionsList[currentProgress-1]

        textViewQuestion = findViewById(R.id.textViewQuestion)
        imageViewFlag = findViewById(R.id.imageViewFlag)
        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.textViewProgress)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        textViewQuestion?.text = question.question
        imageViewFlag?.setImageResource(question.image)
        progressBar?.progress = currentProgress
        textViewProgress?.text = "${currentProgress}/${questionsList.size}"
        optionOne?.text = question.optionFour
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour
    }
}