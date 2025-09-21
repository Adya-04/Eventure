package com.example.eventuree.data.models

data class EventsResponse(
    val message: String,
    val events: List<Events>
)

data class SingleEventResponse(
    val message: String,
    val event: Events
)

data class Events(
    val id: String,
    val name: String,
    val venue: String,
    val description: String,
    val startTime: String,
    val endTime:String,
    val goingCount: Int,
    val eventPic: String,
    val society: Society
)
