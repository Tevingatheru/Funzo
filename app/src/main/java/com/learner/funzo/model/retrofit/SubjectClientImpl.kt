package com.learner.funzo.model.retrofit

import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.model.retrofit.response.SubjectListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class SubjectClientImpl (private val subjectClient: SubjectClient)
{
    suspend fun getAll(): List<SubjectDto> {

        val call: Call<SubjectListResponse> = subjectClient.getAll()
        return withContext(Dispatchers.IO) {
            val response: Response<SubjectListResponse> = call.execute()

            val subjectListResponse: SubjectListResponse = response.body()!!
            val listOfSubjects: List<SubjectDto> = if (subjectListResponse.subjects != null && subjectListResponse.subjects.isNotEmpty()) {
                mapToList(subjectListResponse)
            } else {
                mapToList(SubjectListResponse(ArrayList()))
            }
            listOfSubjects
        }
    }


    private fun mapToList(subjectListResponse: SubjectListResponse): List<SubjectDto> {
        var subjectList : List<SubjectDto> = mutableListOf()
        subjectListResponse.subjects.forEach{
            subjectList = subjectList.plus(SubjectDto(name = it.name, category = it.category))
        }
        return subjectList
    }

}