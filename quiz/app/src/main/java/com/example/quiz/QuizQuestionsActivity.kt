package com.example.quiz

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
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
    private var questionsList: ArrayList<Question>? = null
    private var selectedOption: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        questionsList = Constants.getQuestions()

        textViewQuestion = findViewById(R.id.textViewQuestion)
        imageViewFlag = findViewById(R.id.imageViewFlag)
        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.textViewProgress)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        buttonSubmit?.setOnClickListener(this)

        setQuestions()
    }

    private fun setQuestions() {
        var question: Question = questionsList!![currentProgress-1]
        textViewQuestion?.text = question.question
        imageViewFlag?.setImageResource(question.image)
        progressBar?.progress = currentProgress
        textViewProgress?.text = "${currentProgress}/${progressBar?.max}"
        optionOne?.text = question.optionFour
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        if (currentProgress == questionsList!!.size) {
            buttonSubmit?.text = "FINISH"
        } else {
            buttonSubmit?.text = "SUBMIT"
        }
    }

    private fun setDefaultOptionView() {
        val options: ArrayList<TextView> = ArrayList<TextView>()

        optionOne?.let { options.add(0, it) }
        optionTwo?.let { options.add(1, it) }
        optionThree?.let { options.add(2, it) }
        optionFour?.let { options.add(3, it) }

        for (option in options) {
            option.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun onSelectOption(textViewSelected: TextView, selectedOption: Int) {
        // set default all the option into default
        setDefaultOptionView()
        this.selectedOption = selectedOption

        // set the selected option with styling
        textViewSelected.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
        textViewSelected.setTypeface(textViewSelected.typeface, Typeface.BOLD)
        textViewSelected.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.optionOne -> {
                optionOne?.let { onSelectOption(it, 1) }
            }
            R.id.optionTwo -> {
                optionTwo?.let { onSelectOption(it, 2) }
            }
            R.id.optionThree -> {
                optionThree?.let { onSelectOption(it, 3) }
            }
            R.id.optionFour -> {
                optionFour?.let { onSelectOption(it, 4) }
            }
            R.id.buttonSubmit -> {
                // TODO "Implement button submit"
            }
        }
    }
}