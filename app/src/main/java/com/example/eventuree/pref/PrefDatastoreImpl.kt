package com.example.eventuree.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.eventuree.utils.Constants.AUTH_TOKEN
import com.example.eventuree.utils.Constants.USER_ID
import com.example.eventuree.utils.Constants.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefDatastoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PrefDatastore {

    override fun getToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[stringPreferencesKey(AUTH_TOKEN)] ?: ""
        }
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[stringPreferencesKey(AUTH_TOKEN)] = token
        }
    }

    override fun getUserId(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[stringPreferencesKey(USER_ID)] ?: ""
        }
    }

    override suspend fun saveUserId(userId: String) {
        dataStore.edit {
            it[stringPreferencesKey(USER_ID)] = userId
        }
    }

    override fun getUserName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[stringPreferencesKey(USER_NAME)] ?: ""
        }
    }

    override suspend fun saveUserName(userName: String) {
        dataStore.edit {
            it[stringPreferencesKey(USER_NAME)] = userName
        }
    }

    // Clear all data
    override suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}