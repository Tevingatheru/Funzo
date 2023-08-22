package com.learner.funzo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.learner.funzo.R
import com.learner.funzo.viewModel.ResultActivityViewModel
import com.learner.funzo.viewModel.ResultActivityViewModel.Companion.YOU_FAILED
import com.learner.funzo.viewModel.ResultActivityViewModel.Companion.YOU_PASSED
import com.learner.funzo.viewModel.constant.ScoreConstants

class ResultActivity : AppCompatActivity() {

    private val viewModel: ResultActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        viewModel.initApplicationContext(
            applicationContext = this,
            continueNextBtn = findViewById<Button>(ResultActivityViewModel.playButtonTextView)
        )
        setScore()
        setCompletionText()
    }

    private fun setCompletionText() {
        viewModel.setCompletionText(findViewById<TextView>(ResultActivityViewModel.rCompletionMessageTextView))
    }

    private fun setScore() {
        findViewById<TextView>(ResultActivityViewModel.rScoreTextView).text = viewModel.getScore()
    }

}
