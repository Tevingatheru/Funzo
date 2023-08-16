package com.learner.funzo.model.retrofit

import com.learner.funzo.model.retrofit.request.CreateUserRequest
import com.learner.funzo.model.retrofit.response.CreateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserClient {
    @POST("/users")
    fun createUser(@Body request: CreateUserRequest): Call<CreateUserResponse>
}
