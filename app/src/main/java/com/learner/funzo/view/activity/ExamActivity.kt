package com.learner.funzo.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learner.funzo.R
import com.learner.funzo.model.Exam
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.viewModel.QuizActivityViewModel
import com.learner.funzo.viewModel.constant.ScoreConstants

class ExamActivity : AppCompatActivity() {

    private val viewModel: QuizActivityViewModel by viewModels()

    companion object {
        const val examKey = "exam"
        private const val TAG = "ExamActivity"
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        viewModel.setExam(getParcelableExam())
        viewModel.setExamActivity(this)

        ScoreConstants.resetScore(viewModel.getTotalNoOfQuestions(), viewModel.getThreshold())
        viewModel.setQuestion(this)
    }
    private fun getParcelableExam(): Exam = intent.getParcelableExtra(examKey)!!
}
