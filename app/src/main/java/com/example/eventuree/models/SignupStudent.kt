package com.example.eventuree.models

data class SignupStudentRequest(
    val aboutMe: String,
    val admissionNumber: String,
    val email: String,
    val name: String,
    val password: String,
    val sessionId: String
)

data class SignupStudentResponse(
    val message: String,
    val accessToken: String
)