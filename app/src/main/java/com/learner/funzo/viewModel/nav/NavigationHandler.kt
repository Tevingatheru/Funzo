package com.learner.funzo.viewModel.nav

import android.content.Context
import android.content.Intent
import com.learner.funzo.view.QuizActivity
import com.learner.funzo.view.SubjectListActivity
import com.learner.funzo.viewModel.ExamConstants
import com.learner.funzo.viewModel.QuestionConstants

object NavigationHandler {
    fun navigateToSubjectList(applicationContext: Context) {
        val startIntent = Intent(applicationContext, SubjectListActivity::class.java)
        applicationContext.startActivity(startIntent)
    }

    fun navigateToQuizActivity(applicationContext: Context, questionSize: String) {
        val intent = Intent(applicationContext, QuizActivity::class.java)
        intent.putExtra(QuizActivity.examKey, ExamConstants.getExam())
        intent.putExtra(QuestionConstants.TOTAL_QUESTIONS, questionSize)

        applicationContext.startActivity(intent)
    }

    fun navigateToSubjectListActivity(applicationContext: Context) {
        val intent = Intent(applicationContext, SubjectListActivity::class.java)
        applicationContext.startActivity(intent)
    }
}