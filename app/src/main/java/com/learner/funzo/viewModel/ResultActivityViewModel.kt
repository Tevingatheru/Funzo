package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.learner.funzo.viewModel.nav.NavigationHandler

class ResultActivityViewModel : ViewModel() {
    companion object {
        const val YOU_PASSED = "You Passed"

        const val YOU_FAILED = "You Failed"
    }

    fun navigateToSubjectListActivity(applicationContext: Context) {
        NavigationHandler.navigateToSubjectListActivity(applicationContext)
    }

    fun getScore(): String {
        return String.format(
            "%d / %d",
            ScoreConstants.getScore(),
            ScoreConstants.getTotalNumberOfQuestions()
        )
    }
}
