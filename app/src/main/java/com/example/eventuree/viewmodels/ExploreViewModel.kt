package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.data.models.EventsResponse
import com.example.eventuree.data.repository.MainRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExploreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val allEvents: EventsResponse? = null,
    val trendingEvents: EventsResponse? = null,
    val technicalEvents: EventsResponse? = null,
    val culturalEvents: EventsResponse? = null,
    val selectedCategory: String = "All"
)

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    init {
        fetchAllEvents(1, 10)
    }

    fun fetchAllEvents(page: Int, limit: Int) {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            allEvents = null,
            errorMessage = null,
            selectedCategory = "All"
        )

        viewModelScope.launch {
            when (val result = mainRepository.fetchAllEvents(page, limit)) {
                is NetworkResult.Success -> {
                    _uiState.value = ExploreUiState(
                        isLoading = false,
                        allEvents = result.data
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

    fun fetchTrendingEvents() {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            trendingEvents = null,
            errorMessage = null,
            selectedCategory = "Trending"
        )

        viewModelScope.launch {
            when (val result = mainRepository.fetchTrendingEvents()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        trendingEvents = result.data
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

    fun fetchFilteredEvents(type: String, page: Int, limit: Int) {

        val category = if (type == "technical") "Technical" else "Cultural"

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            selectedCategory = category
        )

        viewModelScope.launch {
            when (val result = mainRepository.fetchFilteredEvents(type, page, limit)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        culturalEvents = result.data
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