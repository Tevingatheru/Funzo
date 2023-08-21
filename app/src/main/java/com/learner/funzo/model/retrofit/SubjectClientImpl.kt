package com.learner.funzo.model.retrofit

import com.learner.funzo.mapper.SubjectDtoMapper
import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.model.retrofit.response.SubjectListResponse
import com.learner.funzo.viewModel.constant.SubjectConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException

class SubjectClientImpl (private val subjectClient: SubjectClient)
{
    suspend fun getAll(): List<SubjectDto> {
        return try {
            withContext(Dispatchers.IO) {
                val call: Call<SubjectListResponse> = subjectClient.getAll()
                val response: Response<SubjectListResponse> = call.execute()
                val subjectListResponse: SubjectListResponse = response.body()!!
                SubjectDtoMapper.mapSubjectListResponseToList(subjectListResponse)
            }
        } catch (e: SocketTimeoutException) {
            SubjectDtoMapper.mapSubjectListToList(SubjectConstants.getSubjects().toList())
        }
    }
}
