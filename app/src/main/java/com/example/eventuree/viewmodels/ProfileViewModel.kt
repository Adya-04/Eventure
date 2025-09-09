package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.data.repository.MainRepository
import com.example.eventuree.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val userName: String = "Guest User",
    val profileImageUrl: String? = null,
    val aboutMe: String = "",
    val following: List<String> = emptyList()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> get() = _uiState

    fun loadProfile(userId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = mainRepository.fetchUserDetails(userId)) {
                is NetworkResult.Success -> {
                    val user = result.data?.user
                    val societies = result.data?.societiesFollowed?.map { it.name } ?: emptyList()

                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        userName = user?.name ?: "Guest User",
                        profileImageUrl = user?.profilePic,
                        aboutMe = user?.aboutMe ?: "",
                        following = societies
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