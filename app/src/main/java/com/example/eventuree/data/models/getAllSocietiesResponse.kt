package com.example.eventuree.data.models

data class getAllSocietiesResponse(
    val message: String,
    val societies: List<Society>
)

data class Society(
    val id: String,
    val name: String,
    val logo: String?
)
