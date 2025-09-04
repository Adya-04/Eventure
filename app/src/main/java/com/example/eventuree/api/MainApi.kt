package com.example.eventuree.api

import com.example.eventuree.models.FollowSocietyRequest
import com.example.eventuree.models.FollowSocietyResponse
import com.example.eventuree.models.PersonalizedEventsResponse
import com.example.eventuree.models.getAllSocietiesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {
    @GET("event/personalized")
    suspend fun getPersonalizedEvents(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PersonalizedEventsResponse>

    @GET("society/all")
    suspend fun getAllSocieties(): Response<getAllSocietiesResponse>

    @POST("follower/follow")
    suspend fun followSociety(
        @Body followSocietyRequest: FollowSocietyRequest
    ): Response<FollowSocietyResponse>
}