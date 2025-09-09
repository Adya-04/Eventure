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
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI State for Home Screen
data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val personalizedEvents: PersonalizedEventsResponse? = null,
    val societies: getAllSocietiesResponse? = null,
    val userMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        fetchPersonalizedEvents(1, 10)
        fetchAllSocieties()
    }

    fun fetchPersonalizedEvents(page: Int, limit: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = mainRepository.fetchPersonalizedEvents(page, limit)) {
                is NetworkResult.Success -> {
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        personalizedEvents = result.data,
                        societies = _uiState.value.societies
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is NetworkResult.Start<*> -> {}
            }
        }
    }

    fun fetchAllSocieties() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = mainRepository.fetchAllSocieties()) {
                is NetworkResult.Success -> {
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        personalizedEvents = _uiState.value.personalizedEvents,
                        societies = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is NetworkResult.Start<*> -> {}
            }
        }
    }

    fun followSociety(societyId: String) {
        viewModelScope.launch {
            when (val result = mainRepository.followSociety(societyId)) {
                is NetworkResult.Success -> {
                    // Refresh societies list to get updated follow status
                    fetchAllSocieties()
                    _uiState.value = _uiState.value.copy(
                        userMessage = result.data?.message ?: "Followed successfully"
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        userMessage = result.message
                    )
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is NetworkResult.Start<*> -> {}
            }
        }
    }

    fun userMessageShown() {
        _uiState.value = _uiState.value.copy(userMessage = null)
    }
}