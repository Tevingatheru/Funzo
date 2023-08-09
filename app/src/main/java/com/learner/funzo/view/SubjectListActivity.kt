package com.learner.funzo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.learner.funzo.FirebaseUtil
import com.learner.funzo.viewModel.ExamConstants
import com.learner.funzo.viewModel.ListHelper
import com.learner.funzo.R
import com.learner.funzo.model.Exam
import com.learner.funzo.viewModel.QuestionConstants
import com.learner.funzo.viewModel.SubjectConstants

class SubjectListActivity : AppCompatActivity() {
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
        val listView = findViewById<ListView>(R.id.listView)

        val listContent = getSubjects()
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listContent)
        ListHelper.getListViewSize(listView)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedSubject = listContent.get(i)
            val subjectName = selectedSubject.name

            val exam = ExamConstants.createExam(subjectName).getExam()

            startQuizActivity(exam)
        }
    }

    private fun getSubjects() = SubjectConstants.getSubjects()

    private fun startQuizActivity(exam: Exam) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra(QuizActivity.examKey, ExamConstants.getExam())
        intent.putExtra(QuestionConstants.TOTAL_QUESTIONS, exam.questions.size.toString())

        startActivity(intent)
        finish()
    }
}
