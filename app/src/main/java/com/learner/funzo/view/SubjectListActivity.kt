package com.learner.funzo.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.learner.funzo.viewModel.ListHelper
import com.learner.funzo.R
import com.learner.funzo.viewModel.SubjectListActivityViewModel

class SubjectListActivity : AppCompatActivity() {
    private val viewModel: SubjectListActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSubjectListView()
    }

    private fun setSubjectListView() {
        val listView = findViewById<ListView>(R.id.listView)
        val subjectListView = viewModel.getSubjectsView()

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectListView)
        ListHelper.getListViewSize(listView)

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedSubject = subjectListView.get(i)
            val exam = viewModel.getExamBySubjectView(selectedSubject)

            viewModel.navigateToQuizActivity(applicationContext = this, exam = exam)
        }
    }
}
