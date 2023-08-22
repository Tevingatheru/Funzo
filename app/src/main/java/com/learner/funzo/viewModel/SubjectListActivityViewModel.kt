package com.learner.funzo.viewModel

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.Exam
import com.learner.funzo.model.retrofit.BackendClientGenerator
import com.learner.funzo.model.retrofit.SubjectClient
import com.learner.funzo.model.retrofit.SubjectClientRepository
import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.viewModel.constant.ExamConstants
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.runBlocking

class SubjectListActivityViewModel: ViewModel()
{
    private lateinit var listView: ListView

    private fun getSubjectsView(): List<SubjectDto> {
        return runBlocking {
            SubjectClientRepository(BackendClientGenerator.createClient(SubjectClient::class.java))
                .getAll()
        }
    }

    private fun getExamBySubjectView(): Exam {
        return ExamConstants.getExamConstants().getExam()
    }

    private fun navigateToQuizActivity(applicationContext: Context, exam: Exam) {
        NavigationHandler.navigateToQuizActivity(
            applicationContext = applicationContext,
            questionSize = exam.questions.size.toString()
        )
    }

    fun setSubjectListView(applicationContext: Context, subjectListView: ListView) {
        listView = subjectListView
        val subjectListView: List<SubjectDto> = this.getSubjectsView()

        listView.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, subjectListView)
        ListHelper.getListViewSize(listView)

        listView.setOnItemClickListener { _, _, i, _ ->
            val exam = this.getExamBySubjectView()
            this.navigateToQuizActivity(applicationContext = applicationContext, exam = exam)
        }
    }
}
