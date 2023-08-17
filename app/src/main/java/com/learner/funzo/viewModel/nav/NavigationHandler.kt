package com.learner.funzo.viewModel.nav

import android.content.Context
import android.content.Intent
import com.learner.funzo.view.LoginActivity
import com.learner.funzo.view.QuizActivity
import com.learner.funzo.view.RegisterActivity
import com.learner.funzo.view.ResultActivity
import com.learner.funzo.view.SubjectListActivity
import com.learner.funzo.viewModel.constant.ExamConstants
import com.learner.funzo.viewModel.constant.QuestionConstants
import com.learner.funzo.viewModel.constant.ScoreConstants

object NavigationHandler {
    fun navigateToSubjectList(applicationContext: Context) {
        val intent = Intent(applicationContext, SubjectListActivity::class.java)
        applicationContext.startActivity(intent)
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

    fun navigateToResultActivity(applicationContext: Context, score: ScoreConstants) {
        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra("score", score)
        applicationContext.startActivity(intent)
    }

    fun navigateToRegistrationActivity(applicationContext: Context) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        applicationContext.startActivity(intent)
    }

    fun navigateToLoginActivity(applicationContext: RegisterActivity) {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        applicationContext.startActivity(intent)
    }
}
