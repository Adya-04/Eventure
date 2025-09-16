package com.example.eventuree.data.api

import com.example.eventuree.data.models.FollowSocietyRequest
import com.example.eventuree.data.models.FollowSocietyResponse
import com.example.eventuree.data.models.GetUserDetailsResponse
import com.example.eventuree.data.models.EventsResponse
import com.example.eventuree.data.models.getAllSocietiesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("event/personalized")
    suspend fun getPersonalizedEvents(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<EventsResponse>

    @GET("society/all")
    suspend fun getAllSocieties(): Response<getAllSocietiesResponse>

    @POST("follower/follow")
    suspend fun followSociety(
        @Body followSocietyRequest: FollowSocietyRequest
    ): Response<FollowSocietyResponse>

    @GET("user/{id}")
    suspend fun getUserDetails(
        @Path("id") id: String
    ): Response<GetUserDetailsResponse>

    @GET("event/all")
    suspend fun getAllEvents(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<EventsResponse>

    @GET("event/trending")
    suspend fun getTrendingEvents(): Response<EventsResponse>

    @GET("search/filter")
    suspend fun getFilteredEvents(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<EventsResponse>
}