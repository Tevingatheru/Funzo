package com.learner.funzo

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

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition:Int = 0
    private var questionList: ArrayList<Question>? = null
    private var exam: Exam? = null
    private var selectedOption: String? = null
    private var correctOption: Options? = null
    private val score : ScoreConstants = ScoreConstants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

//        questionList = intent.getParcelableArrayListExtra("questions")

//        Log.i("question size: ", "${questions.size}" )

        exam = intent.getParcelableExtra("exam")
        questionList = exam!!.questions
        setQuestion()

        val optionA = findViewById<TextView>(R.id.tvOptionA)
        val optionB = findViewById<TextView>(R.id.tvOptionB)
        val optionC = findViewById<TextView>(R.id.tvOptionC)
        val optionD = findViewById<TextView>(R.id.tvOptionD)
        val submitButton = findViewById<Button>(R.id.submitBtn)
        optionA.setOnClickListener ( this )
        optionB.setOnClickListener ( this )
        optionC.setOnClickListener ( this )
        optionD.setOnClickListener ( this )
        submitButton.setOnClickListener ( this )

        score.resetScore(questionList!!.size, exam!!.threshold)
    }

    private fun setQuestion() {
        val optionA = findViewById<TextView>(R.id.tvOptionA)
        val optionB = findViewById<TextView>(R.id.tvOptionB)
        val optionC = findViewById<TextView>(R.id.tvOptionC)
        val optionD = findViewById<TextView>(R.id.tvOptionD)

        val question = questionList!![currentPosition ]

        val questionTextView = findViewById<TextView>(R.id.questionText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val progressText = findViewById<TextView>(R.id.progressBarText)
        val submitButton = findViewById<Button>(R.id.submitBtn)

        defaultOptionsView()
        submitButton.text = "Submit"
        questionTextView.text = question!!.question
        progressBar.progress = currentPosition
        progressText.text = "$currentPosition" + "/" + questionList!!.size
        optionA.text = question.optionA
        optionB.text = question.optionB
        optionC.text = question.optionC
        optionD.text = question.optionD
        correctOption = question.correctOption;
    }

    private fun defaultOptionsView() {
        val optionA = findViewById<TextView>(R.id.tvOptionA)
        val optionB = findViewById<TextView>(R.id.tvOptionB)
        val optionC = findViewById<TextView>(R.id.tvOptionC)
        val optionD = findViewById<TextView>(R.id.tvOptionD)
        val options = ArrayList<TextView>()
        options.add(0, optionA)
        options.add(1, optionB)
        options.add(2, optionC)
        options.add(3, optionD)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_text_background)

        }

    }

    private fun correctAnswerView(selectedOption: String?) {
        // TODO: display correct answer
        when(selectedOption) {
            "A" -> {
                findViewById<TextView>(R.id.tvOptionA).background = ContextCompat.getDrawable(this, R.drawable.wrong_option_text_background)
            }
            "B" -> {
                findViewById<TextView>(R.id.tvOptionB).background = ContextCompat.getDrawable(this, R.drawable.wrong_option_text_background)
            }
            "C" -> {
                findViewById<TextView>(R.id.tvOptionC).background = ContextCompat.getDrawable(this, R.drawable.wrong_option_text_background)
            }
            "D" -> {
                findViewById<TextView>(R.id.tvOptionD).background = ContextCompat.getDrawable(this, R.drawable.wrong_option_text_background)
            }
        }
    }

    override fun onClick(view: View?) {
        Log.i("OnClick", "click: "+ view!!.id)
        Log.i("Result activity", String.format("check count %d >= %d = %s",currentPosition, questionList!!.size, currentPosition >= questionList!!.size))

        when(view?.id ) {
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
                val submitButton = findViewById<Button>(R.id.submitBtn)

                if (currentPosition >= questionList!!.size ) {
                    Toast.makeText(this,
                        "You have completed the quiz",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                }
                else if(selectedOption != null ) {
                    Log.i("QuizActivity", "check: " + (submitButton.text == "Finish"))
                    if (submitButton.text == "Finish") {
                        Log.i("Quiz Activity", "current count = $currentPosition")
                        when {
                            currentPosition < questionList!!.size -> {
                                setQuestion()
                            }
                        }
                    } else {
                        val question = questionList?.get(currentPosition)
                        if (question!!.correctOption != selectedOption?.let { Options.valueOf(it) }) {
                            correctAnswerView(selectedOption)
                        } else {
                            score.addToCorrectResults()
                        }

                        correctOption?.let { showCorrectOption(it) }

                        if (currentPosition+1 >= questionList!!.size) {
                            submitButton.text = "Finish"
                        } else {
                            submitButton.text = "Next"
                        }
                        selectedOption = null
                        currentPosition++
                    }
                }
                else {
                    Toast.makeText(this,
                        "You must select an answer",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    private fun makeSubmitButtonClickable() {

    }

    private fun selectedOptionView(tv: TextView, selectedOption: String) {
        Log.i("OnClick", "selectedOptionView: $selectedOption")
        defaultOptionsView()
        this.selectedOption = selectedOption
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.setTextColor(Color.parseColor("#000000"))
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_text_background)

    }
}