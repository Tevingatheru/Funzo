package com.learner.funzo.retrofit

import com.learner.funzo.retrofit.request.CreateUserRequest
import com.learner.funzo.retrofit.response.CreateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserClient {
    @POST("/users")
    fun createUser(@Body request: CreateUserRequest): Call<CreateUserResponse>
}
