package com.learner.funzo.view

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.learner.funzo.model.Options
import com.learner.funzo.model.Question
import com.learner.funzo.R
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.model.Exam

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition:Int = 0
    private var questionList: ArrayList<Question>? = null
    private var exam: Exam? = null
    private var selectedOption: String? = null
    private var correctOption: Options? = null
    private val score : ScoreConstants = ScoreConstants
    private lateinit var optionB: TextView
    private lateinit var optionC: TextView
    private lateinit var optionD: TextView
    private lateinit var submitButton: Button
    private lateinit var optionA: TextView
    private lateinit var  progressText: TextView
    private lateinit var  progressBar: ProgressBar
    private lateinit var  questionTextView: TextView
    companion object {
        const val examKey = "exam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        exam = intent.getParcelableExtra(examKey)
        questionList = exam!!.questions
        ScoreConstants.resetScore(questionList!!.size, exam!!.threshold)

        initView()
        setOnClickListener()
        setQuestion()
    }

    private fun setOnClickListener() {
        optionA.setOnClickListener(this)
        optionB.setOnClickListener(this)
        optionC.setOnClickListener(this)
        optionD.setOnClickListener(this)
        submitButton.setOnClickListener(this)
    }

    private fun initView() {
        optionA = findViewById<TextView>(R.id.tvOptionA)
        optionB = findViewById<TextView>(R.id.tvOptionB)
        optionC = findViewById<TextView>(R.id.tvOptionC)
        optionD = findViewById<TextView>(R.id.tvOptionD)
        submitButton = findViewById<Button>(R.id.submitBtn)
        questionTextView = findViewById<TextView>(R.id.questionText)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressText = findViewById<TextView>(R.id.progressBarText)
        submitButton = findViewById<Button>(R.id.submitBtn)
    }

    private fun setQuestion() {
        val question = getCurrentQuestion()

        defaultOptionsView()
        submitButton.text = "Submit"
        questionTextView.text = question!!.question
        progressBar.progress = currentPosition
        val text = "$currentPosition / ${questionList!!.size}"
        progressText.text = text
        optionA.text = question.optionA
        optionB.text = question.optionB
        optionC.text = question.optionC
        optionD.text = question.optionD
        correctOption = question.correctOption;
    }

    private fun getCurrentQuestion() = questionList!![currentPosition]

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, findViewById<TextView>(R.id.tvOptionA))
        options.add(1, findViewById<TextView>(R.id.tvOptionB))
        options.add(2, findViewById<TextView>(R.id.tvOptionC))
        options.add(3, findViewById<TextView>(R.id.tvOptionD))

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_text_background
            )
        }
    }

    private fun correctAnswerView(selectedOption: String?) {
        // TODO: display correct answer
        when(selectedOption) {
            "A" -> {
                findViewById<TextView>(R.id.tvOptionA).background = ContextCompat.getDrawable(this,
                    R.drawable.wrong_option_text_background
                )
            }
            "B" -> {
                findViewById<TextView>(R.id.tvOptionB).background = ContextCompat.getDrawable(this,
                    R.drawable.wrong_option_text_background
                )
            }
            "C" -> {
                findViewById<TextView>(R.id.tvOptionC).background = ContextCompat.getDrawable(this,
                    R.drawable.wrong_option_text_background
                )
            }
            "D" -> {
                findViewById<TextView>(R.id.tvOptionD).background = ContextCompat.getDrawable(this,
                    R.drawable.wrong_option_text_background
                )
            }
        }
    }

    override fun onClick(view: View?) {
        Log.i("OnClick", "click: "+ view!!.id)
        Log.i("Result activity", String.format("check count %d >= %d = %s", currentPosition, questionList!!.size,
            isExamComplete()
        ))

        when(view.id) {
            R.id.tvOptionA -> {
                selectedOptionView(findViewById(R.id.tvOptionA), "A")
            }

            R.id.tvOptionB -> {
                selectedOptionView(findViewById(R.id.tvOptionB), "B")
            }

            R.id.tvOptionC -> {
                selectedOptionView(findViewById(R.id.tvOptionC), "C")
            }

            R.id.tvOptionD -> {
                selectedOptionView(findViewById(R.id.tvOptionD), "D")
            }

            R.id.submitBtn -> {
                if (isExamComplete()) {
                    completeExamProcess()
                }
                else if(selectedOption != null ) {
                    incompleteExamProcess()
                }
                else {
                    noAnswerSelectedProcess()
                }
            }
        }
    }

    private fun isExamComplete() = currentPosition >= questionList!!.size

    private fun incompleteExamProcess() {
        Log.i("QuizActivity", "check: " + (submitButton.text == "Finish"))
        if (submitButton.text == "Finish") {
            Log.i("Quiz Activity", "current count = $currentPosition")
            when {
                currentPosition < questionList!!.size -> {
                    setQuestion()
                }
            }
        } else {
            validateAnswer()

            correctOption?.let { showCorrectOption(it) }

            setSubmitButtonText(submitButton)
            selectedOption = null
            currentPosition++
        }
    }

    private fun setSubmitButtonText(submitButton: Button) {
        if (currentPosition + 1 >= questionList!!.size) {
            submitButton.text = "Finish"
        } else {
            submitButton.text = "Next"
        }
    }

    private fun validateAnswer() {
        val question = getCurrentQuestion()
        if (question!!.correctOption != selectedOption?.let { Options.valueOf(it) }) {
            correctAnswerView(selectedOption)
        } else {
            ScoreConstants.addToCorrectResults()
        }
    }

    private fun noAnswerSelectedProcess() {
        Toast.makeText(
            this,
            "You must select an answer",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun completeExamProcess() {
        Toast.makeText(
            this,
            "You have completed the quiz",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
    }

    private fun showCorrectOption (answer: Options) {
        when(answer) {
            Options.A -> {
                findViewById<TextView>(R.id.tvOptionA).background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_text_background
                )
            }
            Options.B -> {
                findViewById<TextView>(R.id.tvOptionB).background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_text_background
                )
            }
            Options.C -> {
                findViewById<TextView>(R.id.tvOptionC).background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_text_background
                )
            }
            Options.D -> {
                findViewById<TextView>(R.id.tvOptionD).background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_text_background
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOption: String) {
        Log.i("OnClick", "selectedOptionView: $selectedOption")
        defaultOptionsView()
        this.selectedOption = selectedOption
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.setTextColor(Color.parseColor("#000000"))
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_text_background
        )
    }
}
