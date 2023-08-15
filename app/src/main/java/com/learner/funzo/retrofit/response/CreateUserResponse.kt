package com.learner.funzo.retrofit.response

data class CreateUserResponse (
    val userCode: String,
    val email: String,
    val userType: String?
)
