package com.example.eventuree.data.repository

import android.util.Log
import com.example.eventuree.data.api.MainApi
import com.example.eventuree.data.models.FollowSocietyRequest
import com.example.eventuree.data.models.FollowSocietyResponse
import com.example.eventuree.data.models.GetUserDetailsResponse
import com.example.eventuree.data.models.EventsResponse
import com.example.eventuree.data.models.SingleEventResponse
import com.example.eventuree.data.models.getAllSocietiesResponse
import com.example.eventuree.utils.NetworkResult
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApi: MainApi) {

    // Fetch Personalized Events
    suspend fun fetchPersonalizedEvents(
        page: Int,
        limit: Int
    ): NetworkResult<EventsResponse> {
        return try {
            val response = mainApi.getPersonalizedEvents(page, limit)
            Log.d("MainAPICall", "$response")
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            Log.d("MainAPICall", e.toString())
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("MainAPICall", e.toString())
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    // Fetch Societies
    suspend fun fetchAllSocieties(): NetworkResult<getAllSocietiesResponse> {
        return try {
            val response = mainApi.getAllSocieties()
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    // Follow Society
    suspend fun followSociety(societyId: String): NetworkResult<FollowSocietyResponse> {
        return try {
            val response = mainApi.followSociety(FollowSocietyRequest(societyId))
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun fetchUserDetails(id: String): NetworkResult<GetUserDetailsResponse> {
        return try {
            val response = mainApi.getUserDetails(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)   // ✅ always non-null here
                } else {
                    NetworkResult.Error("Response body is null")
                }
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun fetchAllEvents(
        page: Int,
        limit: Int
    ): NetworkResult<EventsResponse> {
        return try {
            val response = mainApi.getAllEvents(page, limit)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }


    suspend fun fetchTrendingEvents(): NetworkResult<EventsResponse> {
        return try {
            val response = mainApi.getTrendingEvents()
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun fetchFilteredEvents(
        type: String,
        page: Int,
        limit: Int
    ): NetworkResult<EventsResponse> {
        return try {
            val response = mainApi.getFilteredEvents(type, page, limit)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }

    suspend fun fetchEventDetails(id: String): NetworkResult<SingleEventResponse> {
        return try {
            val response = mainApi.getEventDetails(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("Response body is null")
            } else {
                val errObj = response.errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
                NetworkResult.Error(errObj?.getString("message") ?: "Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error occurred")
        }
    }
}