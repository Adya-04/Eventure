package com.example.eventuree.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val accessToken: String
)