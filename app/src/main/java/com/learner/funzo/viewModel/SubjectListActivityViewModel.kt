package com.learner.funzo.viewModel

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.retrofit.ClientGenerator
import com.learner.funzo.model.retrofit.SubjectClient
import com.learner.funzo.model.retrofit.SubjectClientRepository
import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.util.ListHelper
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.runBlocking

class SubjectListActivityViewModel: ViewModel()
{
    private lateinit var listView: ListView

    private fun getSubjectsView(): List<SubjectDto> {
        return runBlocking {
            SubjectClientRepository()
                .getAll()
        }
    }

    private fun navigateToQuizActivity(applicationContext: Context) {
        NavigationHandler.navigateToExamActivity(
            applicationContext = applicationContext,
        )
    }

    fun setSubjectListView(applicationContext: Context, subjectListView: ListView) {
        listView = subjectListView
        listView.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, this.getSubjectsView())
        ListHelper.adjustListViewHeight(listView)

        listView.setOnItemClickListener { _, _, _, _ ->
            this.navigateToQuizActivity(applicationContext = applicationContext)
        }
    }
}
