package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.data.models.PersonalizedEventsResponse
import com.example.eventuree.data.models.getAllSocietiesResponse
import com.example.eventuree.data.repository.MainRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI States for different screens
data class HomeUiState(
    val personalizedEvents: NetworkResult<PersonalizedEventsResponse> = NetworkResult.Start(null),
    val societies: NetworkResult<getAllSocietiesResponse> = NetworkResult.Start(null),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow< HomeUiState> = _uiState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        fetchPersonalizedEvents(1, 10) // Default page and limit
        fetchAllSocieties()
    }

    fun fetchPersonalizedEvents(page: Int, limit: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = mainRepository.fetchPersonalizedEvents(page, limit)
            _uiState.update {
                it.copy(
                    personalizedEvents = result,
                    isLoading = false,
                    userMessage = if (result is NetworkResult.Error) result.message else null
                )
            }
        }
    }

    fun fetchAllSocieties() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = mainRepository.fetchAllSocieties()
            _uiState.update {
                it.copy(
                    societies = result,
                    isLoading = false,
                    userMessage = if (result is NetworkResult.Error) result.message else null
                )
            }
        }
    }

    fun followSociety(societyId: String) {
        viewModelScope.launch {
            val result = mainRepository.followSociety(societyId)

            // Update societies list if follow was successful
            if (result is NetworkResult.Success) {
                fetchAllSocieties() // Refresh societies list
                _userMessage.value = result.data?.message ?: "Followed successfully"
            } else if (result is NetworkResult.Error) {
                _userMessage.value = result.message
            }
        }
    }

    fun userMessageShown() {
        _userMessage.value = null
    }
}