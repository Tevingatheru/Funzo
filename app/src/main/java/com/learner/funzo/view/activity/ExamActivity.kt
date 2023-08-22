package com.learner.funzo.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learner.funzo.R
import com.learner.funzo.model.Exam
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.viewModel.QuizActivityViewModel
import com.learner.funzo.viewModel.constant.ScoreConstants
import java.lang.Exception

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
            R.id.itemLogout -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        try {
            super.onResume()
            Log.i(TAG, "onResume")
        } catch (e: Exception) {
            Log.w(TAG, "Logged error")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
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
