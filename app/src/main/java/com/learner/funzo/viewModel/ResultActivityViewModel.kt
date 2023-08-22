package com.learner.funzo.viewModel

import android.content.Context
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.learner.funzo.R
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.nav.NavigationHandler

class ResultActivityViewModel : ViewModel(), OnClickListener {
    private var applicationContext: Context? = null
    lateinit var continueNextBtn: Button

    companion object {
        const val YOU_PASSED = "You Passed"
        const val YOU_FAILED = "You Failed"
        val playButtonTextView = R.id.playButton
        val rScoreTextView = R.id.score
        val rCompletionMessageTextView = R.id.completionMessage
    }

    fun initApplicationContext(applicationContext: Context, continueNextBtn: Button) {
        this.applicationContext = applicationContext
        this.continueNextBtn = continueNextBtn

        continueNextBtn.setOnClickListener(this)
    }

    private fun navigateToSubjectListActivity(applicationContext: Context) {
        NavigationHandler.navigateToSubjectListActivity(applicationContext)
    }

    fun getScore(): String {
        return String.format(
            "%d / %d",
            ScoreConstants.getTotalCorrectAnswerCount(),
            ScoreConstants.getTotalNumberOfQuestions()
        )
    }

    fun setCompletionText(completionText: TextView) {
        if (ScoreConstants.passed()) {
            completionText.text = YOU_PASSED
        } else {
            completionText.text = YOU_FAILED
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.playButton -> {
                this.navigateToSubjectListActivity(applicationContext = applicationContext!!)
            }
        }
    }
}
