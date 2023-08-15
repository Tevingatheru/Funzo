package com.learner.funzo.retrofit

import com.learner.funzo.retrofit.response.SubjectListResponse
import retrofit2.Call
import retrofit2.http.GET

interface SubjectClient {
    @GET("/subjects")
    fun getAll(): Call<SubjectListResponse>
}
