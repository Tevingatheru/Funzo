package com.learner.funzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

class SubjectListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
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
}