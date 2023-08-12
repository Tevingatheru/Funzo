package com.learner.funzo.retrofit

import com.learner.funzo.retrofit.dto.SubjectListDto
import retrofit2.Call
import retrofit2.http.GET

interface SubjectClient {
    @GET("/subjects")
    fun getAll(): Call<SubjectListDto>
}
