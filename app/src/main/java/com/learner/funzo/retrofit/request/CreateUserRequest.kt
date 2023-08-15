package com.learner.funzo.retrofit.request

data class CreateUserRequest(
    val userType: String = "student",
    val email: String
) {

}
