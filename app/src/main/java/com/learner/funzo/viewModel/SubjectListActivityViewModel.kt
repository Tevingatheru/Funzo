package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.Exam
import com.learner.funzo.model.Subject
import com.learner.funzo.view.SubjectView
import com.learner.funzo.viewModel.nav.NavigationHandler

class SubjectListActivityViewModel: ViewModel()
{
    private fun getSubjects(): List<Subject> {
        return SubjectConstants.getSubjects()
    }

    fun getSubjectsView(): List<SubjectView> {
        var subjectViewList: List<SubjectView> = arrayListOf()
        getSubjects().forEach{
            subjectViewList = subjectViewList.plus(
                SubjectView(
                    name = it.name,
                    category = it.category
                ))
        }
        return subjectViewList
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
