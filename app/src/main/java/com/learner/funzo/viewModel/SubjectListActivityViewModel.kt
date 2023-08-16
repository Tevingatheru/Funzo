package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.Exam
import com.learner.funzo.model.retrofit.BackendClientGenerator
import com.learner.funzo.model.retrofit.SubjectClient
import com.learner.funzo.model.retrofit.SubjectClientImpl
import com.learner.funzo.view.SubjectView
import com.learner.funzo.viewModel.constant.ExamConstants
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.runBlocking

class SubjectListActivityViewModel: ViewModel()
{

    fun getSubjectsView(): List<SubjectView> {
        return runBlocking {
            SubjectClientImpl(BackendClientGenerator.createClient(SubjectClient::class.java)).getAll()
        }
    }

    fun getExamBySubjectView(subjectView: SubjectView): Exam {
        return ExamConstants.createExam(subjectView.name).getExam()
    }

    fun navigateToQuizActivity(applicationContext: Context, exam: Exam) {
        NavigationHandler.navigateToQuizActivity(
            applicationContext = applicationContext,
            questionSize = exam.questions.size.toString()
        )
    }
}
