package com.bangkit.purrfectaid.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.purrfectaid.domain.model.User
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by Yosua on 12/06/2023
 */
class DataStoreRepositoryImpl(private val dataStore: DataStore<Preferences>) : DataStoreRepository {

    private val name = stringPreferencesKey("name")
    private val email = stringPreferencesKey("email")
    private val token = stringPreferencesKey("token")

    override suspend fun setUser(user: User) {

    }

    override suspend fun setToken(token: String) {
        dataStore.edit { pref ->
            pref[this.token] = token
        }
    }

    override suspend fun getToken(): String? {
        return dataStore.data.map { pref ->
            pref[token]
        }.first()
    }

    override suspend fun logout(): Boolean {
        return try {
            dataStore.edit { pref ->
                pref.clear()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}