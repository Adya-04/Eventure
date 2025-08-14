package com.example.eventuree.pref

import kotlinx.coroutines.flow.Flow

interface PrefDatastore {
    fun getToken(): Flow<String>

    suspend fun saveToken(token: String)
}