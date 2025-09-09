package com.example.eventuree.data.models

data class GetUserDetailsResponse(
    val societiesFollowed: List<SocietiesFollowed>,
    val user: UserDetails
)
data class SocietiesFollowed(
    val description: String,
    val id: String,
    val logo: String,
    val name: String,
    val type: String
)

data class UserDetails(
    val aboutMe: String,
    val admissionNumber: String,
    val email: String,
    val googleId: String,
    val id: String,
    val name: String,
    val password: String,
    val profilePic: String,
    val provider: String,
    val role: String
)