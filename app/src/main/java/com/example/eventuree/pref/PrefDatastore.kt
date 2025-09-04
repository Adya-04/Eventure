package com.example.eventuree.pref

import kotlinx.coroutines.flow.Flow

interface PrefDatastore {

    fun getToken(): Flow<String>
    suspend fun saveToken(token: String)

    suspend fun saveUserId(userId: String)
    fun getUserId(): Flow<String>

    suspend fun saveUserName(userName: String)
    fun getUserName(): Flow<String>

    suspend fun clearAll()
}
