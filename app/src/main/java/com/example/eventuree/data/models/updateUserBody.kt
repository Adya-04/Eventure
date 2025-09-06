package com.example.eventuree.data.models

data class UpdateUserRequest(
    val name: String,
    val email: String,
    val admissionNumber: String,
    val aboutMe: String
)

data class UpdateUserResponse(
    val id: String,
    val name: String,
    val email: String,
    val message: String
)