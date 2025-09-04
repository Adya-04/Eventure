package com.example.eventuree.models

data class SignupSocietyRequest(
    val admissionNumber: String,
    val email: String,
    val name: String,
    val password: String,
    val societyDescription: String,
    val societyName: String,
    val societyType: String
)

data class SignupSocietyResponse(
    val message: String,
    val accessToken: String
)