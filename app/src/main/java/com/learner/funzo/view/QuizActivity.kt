package com.learner.funzo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R
import com.learner.funzo.model.Question
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.QuizActivityViewModel

class QuizActivity : AppCompatActivity()
{
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

        viewModel.setExamActivity(this)

        val question: Question = this.getCurrentQuestion()

        initView()
        viewModel.setMCQuestion(question)
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

    private fun initView() {
        viewModel.initMCQQuestion(optionA = findViewById<TextView>(R.id.tvOptionA),
        optionB = findViewById<TextView>(R.id.tvOptionB),
        optionC = findViewById<TextView>(R.id.tvOptionC),
        optionD = findViewById<TextView>(R.id.tvOptionD),
        submitButton = findViewById<Button>(R.id.submitBtn),
        questionTextView = findViewById<TextView>(R.id.questionText),
        progressBar = findViewById<ProgressBar>(R.id.progressBar),
        progressText = findViewById<TextView>(R.id.progressBarText))
    }

    private fun getCurrentQuestion() = viewModel.getCurrentQuestionByPosition()

}
