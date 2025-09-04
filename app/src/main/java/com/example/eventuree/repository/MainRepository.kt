package com.example.eventuree.repository

import android.util.Log
import com.example.eventuree.api.MainApi
import com.example.eventuree.models.FollowSocietyRequest
import com.example.eventuree.models.FollowSocietyResponse
import com.example.eventuree.models.PersonalizedEventsResponse
import com.example.eventuree.models.getAllSocietiesResponse
import com.example.eventuree.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject

class MainRepository@Inject constructor(private val mainApi: MainApi) {

    //Personalized Events Flow
    private val _getPersEvents = MutableStateFlow<NetworkResult<PersonalizedEventsResponse>>(NetworkResult.Start(null))
    val getPersEvents: StateFlow<NetworkResult<PersonalizedEventsResponse>>
        get() = _getPersEvents

    // Societies Flow
    private val _getSocieties = MutableStateFlow<NetworkResult<getAllSocietiesResponse>>(NetworkResult.Start(null))
    val getSocieties: StateFlow<NetworkResult<getAllSocietiesResponse>> get() = _getSocieties

    // Follow Society Flow
    private val _followSociety =
        MutableStateFlow<NetworkResult<FollowSocietyResponse>>(NetworkResult.Start(null))
    val followSociety: StateFlow<NetworkResult<FollowSocietyResponse>> get() = _followSociety


    // Fetch Personalized Events
    suspend fun fetchPersonalizedEvents(page: Int, limit: Int) {
        _getPersEvents.value = NetworkResult.Loading()
        try {
            val response = mainApi.getPersonalizedEvents(page, limit)
            Log.d("MainAPICall", "$response")
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("AuthAPICall", "$responseBody")
                    _getPersEvents.value = (NetworkResult.Success(responseBody))
                } else {
                    _getPersEvents.value = (NetworkResult.Error("Response body is null"))
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                Log.d("AuthAPICall", errObj.toString())
                _getPersEvents.value = (NetworkResult.Error(errObj.getString("message")))
            } else {
                Log.d("AuthAPICall", response.errorBody().toString())
                _getPersEvents.value = (NetworkResult.Error("Something went wrong"))
            }
        } catch (e: SocketTimeoutException) {
            Log.d("MainAPICall", e.toString())
            _getPersEvents.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            Log.d("MainAPICall", e.toString())
            _getPersEvents.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    // Fetch Societies
    suspend fun fetchAllSocieties() {
        _getSocieties.value = NetworkResult.Loading()
        try {
            val response = mainApi.getAllSocieties()
            Log.d("MainAPICall", "$response")
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("SocietyAPICall", "$responseBody")
                    _getSocieties.value = NetworkResult.Success(responseBody)
                } else {
                    _getSocieties.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _getSocieties.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                _getSocieties.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            _getSocieties.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            _getSocieties.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    // Follow Society
    suspend fun followSociety(societyId: String) {
        _followSociety.value = NetworkResult.Loading()
        try {
            val response = mainApi.followSociety(FollowSocietyRequest(societyId))
            Log.d("FollowSocietyAPICall", "$response")
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _followSociety.value = NetworkResult.Success(responseBody)
                } else {
                    _followSociety.value = NetworkResult.Error("Response body is null")
                }
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _followSociety.value = NetworkResult.Error(errObj.getString("message"))
            } else {
                _followSociety.value = NetworkResult.Error("Something went wrong")
            }
        } catch (e: SocketTimeoutException) {
            _followSociety.value = NetworkResult.Error("Please try again!")
        } catch (e: Exception) {
            _followSociety.value = NetworkResult.Error("Unexpected error occurred")
        }
    }

    fun resetFollowState() {
        _followSociety.value = NetworkResult.Start(null)
    }

}