package com.learner.funzo.retrofit

import com.learner.funzo.retrofit.dto.SubjectDto
import retrofit2.http.GET


interface SubjectClient {
    @GET("/subjects")
    fun getAll(): List<SubjectDto>
}
