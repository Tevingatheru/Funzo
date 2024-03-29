package com.learner.funzo.viewModel.nav

import android.content.Context
import android.content.Intent
import com.learner.funzo.view.activity.ExamActivity
import com.learner.funzo.view.activity.LoginActivity
import com.learner.funzo.view.activity.MainActivity
import com.learner.funzo.view.activity.RegisterActivity
import com.learner.funzo.view.activity.ResultActivity
import com.learner.funzo.view.activity.SubjectListActivity
import com.learner.funzo.viewModel.constant.ExamConstants
import com.learner.funzo.viewModel.constant.ScoreConstants
import kotlinx.coroutines.runBlocking

object NavigationHandler {
    fun navigateToSubjectList(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, SubjectListActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToSubjectListActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, SubjectListActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToResultActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, ResultActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToRegistrationActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToLoginActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToMainActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }

    fun navigateToExamActivity(applicationContext: Context) {
        runBlocking {
            val intent = Intent(applicationContext, ExamActivity::class.java)
            intent.putExtra(ExamActivity.examKey, ExamConstants.getExam())

            applicationContext.startActivity(intent)
        }
    }
}
