package com.learner.funzo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.learner.funzo.util.FirebaseUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.learner.funzo.viewModel.ListHelper
import com.learner.funzo.R
import com.learner.funzo.viewModel.SubjectListActivityViewModel

class SubjectListActivity : AppCompatActivity() {
    private val viewModel: SubjectListActivityViewModel by viewModels()
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
        setSubjectListView()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setSubjectListView() {
        listView = findViewById<ListView>(R.id.listView)
        val subjectListView = viewModel.getSubjectsView()

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectListView)
        ListHelper.getListViewSize(listView)

        listView.setOnItemClickListener { _, _, i, _ ->
            val selectedSubject = subjectListView.get(i)
            val exam = viewModel.getExamBySubjectView(selectedSubject)

            viewModel.navigateToQuizActivity(applicationContext = this, exam = exam)
        }
    }
}
