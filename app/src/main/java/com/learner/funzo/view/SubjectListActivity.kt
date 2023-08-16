package com.learner.funzo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.learner.funzo.FirebaseUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.learner.funzo.viewModel.ListHelper
import com.learner.funzo.R
import com.learner.funzo.viewModel.SubjectListActivityViewModel
import androidx.appcompat.app.AppCompatActivity
import com.learner.funzo.retrofit.BackendClientGenerator
import com.learner.funzo.retrofit.SubjectClient
import com.learner.funzo.retrofit.response.SubjectListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class SubjectListActivity : AppCompatActivity() {
    private val viewModel: SubjectListActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
        setSubjectListView()
    }

    private suspend fun getSubjects(): List<Subject> {
        val subjectClient: SubjectClient = BackendClientGenerator.createClient(SubjectClient::class.java)

        val call: Call<SubjectListResponse> = subjectClient.getAll()

        return withContext(Dispatchers.IO) {
            val response: Response<SubjectListResponse> = call.execute()

            val subjectListResponse: SubjectListResponse = response.body()!!
            val listOfSubjects: List<Subject> = if (subjectListResponse.subjects != null && subjectListResponse.subjects.isNotEmpty()) {
                mapToList(subjectListResponse)
            } else {
                mapToList(SubjectListResponse(ArrayList()))
            }
            listOfSubjects
        }
    }

    private fun mapToList(subjectListResponse: SubjectListResponse): List<Subject> {
        var subjectList : List<Subject> = mutableListOf()
        val i = 1
        subjectListResponse.subjects.forEach{
            subjectList = subjectList.plus(Subject(i, it.name, it.category))
        }
        return subjectList
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
