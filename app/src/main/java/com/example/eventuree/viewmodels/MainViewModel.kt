package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.models.FollowSocietyResponse
import com.example.eventuree.models.PersonalizedEventsResponse
import com.example.eventuree.models.getAllSocietiesResponse
import com.example.eventuree.repository.MainRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    // Personalized Events
    val getPersEvents: StateFlow<NetworkResult<PersonalizedEventsResponse>>
        get() = mainRepository.getPersEvents

    fun fetchPersonalizedEvents(page: Int, limit: Int) {
        viewModelScope.launch {
            mainRepository.fetchPersonalizedEvents(page, limit)
        }
    }

    // Societies
    val getSocieties: StateFlow<NetworkResult<getAllSocietiesResponse>>
        get() = mainRepository.getSocieties

    fun fetchAllSocieties() {
        viewModelScope.launch {
            mainRepository.fetchAllSocieties()
        }
    }

    // Follow Society
    val followSociety: StateFlow<NetworkResult<FollowSocietyResponse>>
        get() = mainRepository.followSociety

    fun followSociety(societyId: String) {
        viewModelScope.launch {
            mainRepository.followSociety(societyId)
        }
    }

    fun resetFollowState() {
        mainRepository.resetFollowState()
    }
}