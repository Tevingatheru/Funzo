package com.learner.funzo.model.retrofit

import com.learner.funzo.model.retrofit.request.CreateUserRequest
import com.learner.funzo.model.retrofit.response.CreateUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserClientImpl (private val userClient: UserClient) {
    suspend fun createUser(request: CreateUserRequest): Response<CreateUserResponse> {

        return withContext(Dispatchers.IO) {
            userClient.createUser(request).execute()
        }
    }
}
