package com.example.eventuree.data.models

data class UpdateOrganiserRequest(
    val name: String? = null,
    val email: String? = null,
    val societyName: String,
    val societyDescription: String,
    val societyType: String,
    val admissionNumber: String? = null,
    val sessionId: String? = null
)

data class UpdateOrganiserResponse(
    val message: String,
    val society: SocietyInfo
)

data class SocietyInfo(
    val id: String,
    val name: String,
    val description: String,
    val type: String
)

