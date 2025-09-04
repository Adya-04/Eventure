package com.example.eventuree.models

data class PersonalizedEventsResponse(
    val message: String,
    val events: List<Events>
)

data class Events(
    val name: String,
    val venue: String,
    val startTime: String,
    val goingCount: Int
)
