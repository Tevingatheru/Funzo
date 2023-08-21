package com.learner.funzo.model.retrofit.request

data class CreateUserRequest(
    val userType: String = "student",
    val email: String
) {

}
