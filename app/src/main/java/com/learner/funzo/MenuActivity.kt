package com.learner.funzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val listView = findViewById<ListView>(R.id.listView)

        val listContent = SubjectConstants.getSubjects()
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listContent)
        ListHelper.getListViewSize(listView)
        listView.setOnItemClickListener { adapterView, view, i, l ->
//            Log.i("Check", listContent.get(i).toString() + " id: " + l )
            val selectedSubject = listContent.get(i)
            val subjectName = selectedSubject.name
            val examConstants = ExamConstants

            val exam = examConstants.createExam(subjectName).getExam()

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("exam", examConstants.getExam())
            intent.putExtra(QuestionConstants.TOTAL_QUESTIONS, exam.questions.size.toString() )
            intent.putExtra(QuestionConstants.CORRECT_ANSWERS, "0")
            startActivity(intent)
            finish()
        }
    }
}