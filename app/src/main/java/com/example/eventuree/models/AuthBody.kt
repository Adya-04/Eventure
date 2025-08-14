package com.example.eventuree.models

data class AuthLoginRequest(
    val idToken: String
)

data class AuthLoginResponse(
    val message: String,
    val token: String,
    val user: UserData,
    val isLoggingInWithGoogle: Boolean
)

data class UserData(
    val id: String,
    val email: String,
    val name: String,
    val profilePic: String
)