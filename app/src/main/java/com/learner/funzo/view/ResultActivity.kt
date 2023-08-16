package com.learner.funzo.view

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

class ResultActivity : AppCompatActivity() , OnClickListener {

    private val viewModel: ResultActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setNextButtonOnClickListener()
        setScore()
        setCompletionText()
    }

    private fun setCompletionText() {
        val completionText = findViewById<TextView>(R.id.completionMessage)
        if (ScoreConstants.passed()) {
            completionText.text = YOU_PASSED
        } else {
            completionText.text = YOU_FAILED
        }
    }

    private fun setScore() {
        findViewById<TextView>(R.id.score).text = viewModel.getScore()
    }

    private fun setNextButtonOnClickListener() {
        findViewById<Button>(R.id.playButton).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Log.i("Results Activity",  "id: + ${view?.id.toString()} ")
        when(view?.id) {
            R.id.playButton -> {
                viewModel.navigateToSubjectListActivity(this)
                finish()
            }
        }
    }
}
