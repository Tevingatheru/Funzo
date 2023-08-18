package com.learner.funzo.view

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.model.Options
import com.learner.funzo.R
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.QuizActivityViewModel
import com.learner.funzo.viewModel.QuizActivityViewModel.Companion.FINISH
import com.learner.funzo.viewModel.QuizActivityViewModel.Companion.NEXT
import com.learner.funzo.viewModel.QuizActivityViewModel.Companion.SUBMIT

class QuizActivity : AppCompatActivity(), View.OnClickListener
{
    private lateinit var optionB: TextView
    private lateinit var optionC: TextView
    private lateinit var optionD: TextView
    private lateinit var submitButton: Button
    private lateinit var optionA: TextView
    private lateinit var  progressText: TextView
    private lateinit var  progressBar: ProgressBar
    private lateinit var  questionTextView: TextView

    private val viewModel: QuizActivityViewModel by viewModels()

    companion object {
        const val examKey = "exam"
        private const val TAG = "QuizActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        viewModel.setExam(intent.getParcelableExtra(examKey)!!)
        ScoreConstants.resetScore(viewModel.getTotalNoOfQuestions(), viewModel.getThreshold())

        initView()
        setOnClickListener()
        setQuestion()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        submitButton.text = SUBMIT
        questionTextView.text = question!!.question
        optionA.text = question.optionA
        optionB.text = question.optionB
        optionC.text = question.optionC
        optionD.text = question.optionD
        viewModel.setCorrectOption(question.correctOption!!)

        setProgressBar()
    }

    private fun setProgressBar() {
        val currentPosition = viewModel.getCurrentPosition()
        progressBar.progress = currentPosition
        val text = "$currentPosition / ${viewModel.getTotalNoOfQuestions()}"
        progressText.text = text
    }

    private fun getCurrentQuestion() = viewModel.getCurrentQuestionByPosition(viewModel.getCurrentPosition())

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(findViewById(R.id.tvOptionA))
        options.add(findViewById(R.id.tvOptionB))
        options.add(findViewById(R.id.tvOptionC))
        options.add(findViewById(R.id.tvOptionD))

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
        Log.i(TAG, "click: "+ view!!.id)

        when(view.id) {
            R.id.tvOptionA -> {
                selectedOptionView(tv = findViewById(R.id.tvOptionA), selectedOption = "A")
            }

            R.id.tvOptionB -> {
                selectedOptionView(tv = findViewById(R.id.tvOptionB), selectedOption = "B")
            }

            R.id.tvOptionC -> {
                selectedOptionView(tv = findViewById(R.id.tvOptionC), selectedOption = "C")
            }

            R.id.tvOptionD -> {
                selectedOptionView(tv = findViewById(R.id.tvOptionD), selectedOption = "D")
            }

            R.id.submitBtn -> {
                if (isExamComplete()) {
                    completeExamProcess()
                }
                else if(viewModel.selectedOption != null ) {
                    incompleteExamProcess()
                }
                else if (isNextPhase()) {
                    setQuestion()
                }
                else
                {
                    noAnswerSelectedProcess()
                }
            }
        }
    }

    private fun isNextPhase(): Boolean {
        return submitButton.text == NEXT
    }

    private fun isExamComplete() = viewModel.getCurrentPosition() >= viewModel.getTotalNoOfQuestions()

    private fun incompleteExamProcess() {
        Log.i(TAG, "check: " + (submitButton.text == FINISH))
        if (submitButton.text == FINISH) {
            when {
                viewModel.getCurrentPosition() < viewModel.getTotalNoOfQuestions() -> {
                    setQuestion()
                }
            }
        } else {
            validateAnswer(viewModel.selectedOption)

            viewModel.getCorrectOption()?.let { showCorrectOption(it) }

            setSubmitButtonText(submitButton)
            viewModel.selectedOption = null
            viewModel.nextPosition()
        }
    }

    private fun setSubmitButtonText(submitButton: Button) {
        if (isExamComplete()) {
            submitButton.text = FINISH
        } else {
            submitButton.text = NEXT
        }
    }

    private fun validateAnswer(selectedOption: String?) {
        val question = getCurrentQuestion()

        if (question.correctOption != selectedOption?.let { Options.valueOf(it) }) {
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
        viewModel.navigateToResultActivity(applicationContext = this)
        finish()
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
        Log.i(TAG, "selectedOptionView: $selectedOption")
        defaultOptionsView()
        viewModel.selectedOption = selectedOption
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.setTextColor(Color.parseColor("#000000"))
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_text_background
        )
    }
}
