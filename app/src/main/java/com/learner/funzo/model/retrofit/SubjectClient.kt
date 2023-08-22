package com.learner.funzo.model.retrofit

import com.learner.funzo.model.retrofit.response.SubjectListResponse
import retrofit2.Call
import retrofit2.http.GET

interface SubjectClient {
    @GET("/subjects")
    suspend fun getAll(): Call<SubjectListResponse>
}
