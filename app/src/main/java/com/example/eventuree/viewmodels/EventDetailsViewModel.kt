package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.data.models.EventsResponse
import com.example.eventuree.data.models.SingleEventResponse
import com.example.eventuree.data.repository.MainRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val eventDetails: SingleEventResponse? = null
)

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState

    fun fetchEventDetails(eventId: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            eventDetails = null,
            errorMessage = null
        )

        viewModelScope.launch {
            when (val result = mainRepository.fetchEventDetails(eventId)) {
                is NetworkResult.Success -> {
                    _uiState.value = EventDetailsUiState(
                        isLoading = false,
                        eventDetails = result.data
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
}