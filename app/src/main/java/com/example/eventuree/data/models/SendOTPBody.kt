package com.example.eventuree.data.models

data class SendOTPRequest(
    val email: String
)

data class SendOTPResponse(
    val message: String
)