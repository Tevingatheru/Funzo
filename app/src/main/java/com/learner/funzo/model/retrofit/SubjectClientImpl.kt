package com.learner.funzo.model.retrofit

import com.learner.funzo.model.retrofit.response.SubjectListResponse
import com.learner.funzo.view.SubjectView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class SubjectClientImpl (private val subjectClient: SubjectClient)
{
    suspend fun getAll(): List<SubjectView> {

        val call: Call<SubjectListResponse> = subjectClient.getAll()
        return withContext(Dispatchers.IO) {
            val response: Response<SubjectListResponse> = call.execute()

            val subjectListResponse: SubjectListResponse = response.body()!!
            val listOfSubjects: List<SubjectView> = if (subjectListResponse.subjects != null && subjectListResponse.subjects.isNotEmpty()) {
                mapToList(subjectListResponse)
            } else {
                mapToList(SubjectListResponse(ArrayList()))
            }
            listOfSubjects
        }
    }


    private fun mapToList(subjectListResponse: SubjectListResponse): List<SubjectView> {
        var subjectList : List<SubjectView> = mutableListOf()
        subjectListResponse.subjects.forEach{
            subjectList = subjectList.plus(SubjectView(name = it.name, category = it.category))
        }
        return subjectList
    }

}