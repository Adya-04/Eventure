package com.example.eventuree.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class User(
    val id: String,
    val name: String
)

data class LoginResponse(
    val message: String,
    val accessToken: String,
    val user: User
)