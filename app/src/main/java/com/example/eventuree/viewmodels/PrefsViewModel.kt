package com.example.eventuree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventuree.pref.PrefDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrefsViewModel @Inject constructor(
    private val prefs: PrefDatastore
) : ViewModel() {

    //Expose token
    val token: Flow<String> = prefs.getToken()

    fun saveToken(token: String) {
        viewModelScope.launch {
            prefs.saveToken(token)
        }
    }
    fun clearAll() {
        viewModelScope.launch {
            prefs.clearAll()
        }
    }

    val userId: Flow<String> = prefs.getUserId()

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            prefs.saveUserId(userId)
        }
    }

    val userName: Flow<String> = prefs.getUserName()

    fun saveUserName(userName: String) {
        viewModelScope.launch {
            prefs.saveUserName(userName)
        }
    }
}