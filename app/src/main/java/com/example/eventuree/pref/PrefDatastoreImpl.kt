package com.example.eventuree.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.eventuree.utils.Constants.AUTH_TOKEN
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

}