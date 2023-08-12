package com.learner.funzo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.learner.funzo.retrofit.SubjectClient
import com.learner.funzo.retrofit.dto.SubjectListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubjectListActivity : AppCompatActivity() {
    var httpClient = OkHttpClient.Builder()
    var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
        val listView = findViewById<ListView>(R.id.listView)

        var listOfSubjects: List<Subject>
        runBlocking {
            listOfSubjects = getSubjects()
            listView(listOfSubjects, listView)
        }
    }

    private fun listView(
        listOfSubjects: List<Subject>,
        listView: ListView
    ) {
        extracted(listOfSubjects, listView)
    }

    private fun extracted(
        listOfSubjects: List<Subject>,
        listView: ListView
    ) {
        val listContent = listOfSubjects
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
            intent.putExtra(QuestionConstants.TOTAL_QUESTIONS, exam.questions.size.toString())
            intent.putExtra(QuestionConstants.CORRECT_ANSWERS, "0")
            startActivity(intent)
            finish()
        }
    }

    private suspend fun getSubjects(): List<Subject> {
        val subjectClient: SubjectClient = retrofit.create(SubjectClient::class.java)

        val call: Call<SubjectListDto> = subjectClient.getAll()

        return withContext(Dispatchers.IO) {
            val response: Response<SubjectListDto> = call.execute()

            val subjectListDto: SubjectListDto = response.body()!!


            val listOfSubjects: List<Subject> = mapToList(subjectListDto)
            listOfSubjects
        }
    }

    private fun mapToList(subjectListDto: SubjectListDto): List<Subject> {
        var subjectList : List<Subject> = mutableListOf()
        val i = 1
        subjectListDto.listSubjectDto.forEach{
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
}