package com.example.eventuree.models

data class SendOTPRequest(
    val email: String
)

data class SendOTPResponse(
    val message: String
)