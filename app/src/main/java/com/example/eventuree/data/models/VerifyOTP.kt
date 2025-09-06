package com.example.eventuree.data.models

data class VerifyOTPRequest(
    val email: String,
    val otp: String
)

data class VerifyOTPResponse(
    val message: String
)