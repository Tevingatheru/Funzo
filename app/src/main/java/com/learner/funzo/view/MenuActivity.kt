package com.learner.funzo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.learner.funzo.model.ExamConstants
import com.learner.funzo.model.ListHelper
import com.learner.funzo.model.QuestionConstants
import com.learner.funzo.R
import com.learner.funzo.model.SubjectConstants

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val listView = findViewById<ListView>(R.id.listView)

        val listContent = SubjectConstants.getSubjects()
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listContent)
        ListHelper.getListViewSize(listView)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val exam = ExamConstants.getExam()

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("exam", ExamConstants.getExam())
            intent.putExtra(QuestionConstants.TOTAL_QUESTIONS, exam.questions.size.toString() )
            intent.putExtra(QuestionConstants.CORRECT_ANSWERS, "0")
            startActivity(intent)
            finish()
        }
    }
}