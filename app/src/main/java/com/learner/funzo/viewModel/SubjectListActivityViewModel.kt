package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.Exam
import com.learner.funzo.model.retrofit.BackendClientGenerator
import com.learner.funzo.model.retrofit.SubjectClient
import com.learner.funzo.model.retrofit.SubjectClientImpl
import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.viewModel.constant.ExamConstants
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.runBlocking

class SubjectListActivityViewModel: ViewModel()
{
    fun getSubjectsView(): List<SubjectDto> {
        return runBlocking {
            SubjectClientImpl(BackendClientGenerator.createClient(SubjectClient::class.java))
                .getAll()
        }
    }

    fun getExamBySubjectView(subjectDto: SubjectDto): Exam {
        return ExamConstants.createExam(subjectDto.name).getExam()
    }

    fun navigateToQuizActivity(applicationContext: Context, exam: Exam) {
        NavigationHandler.navigateToQuizActivity(
            applicationContext = applicationContext,
            questionSize = exam.questions.size.toString()
        )
    }
}
