package com.bangkit.purrfectaid.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.purrfectaid.domain.model.User
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by Yosua on 12/06/2023
 */
class DataStoreRepositoryImpl(private val dataStore: DataStore<Preferences>, private val dataToken: DataStore<Preferences>) : DataStoreRepository {

    private val id = intPreferencesKey("id")
    private val name = stringPreferencesKey("name")
    private val email = stringPreferencesKey("email")
//    private val image = stringPreferencesKey("image")
    private val userAuthProvider = intPreferencesKey("user_auth_provider")
    private val userIsNativeRegistration = intPreferencesKey("userIsNativeRegistration")

    private val token = stringPreferencesKey("token")

    override suspend fun setUser(user: User) {
        dataStore.edit { pref ->
            pref[this.id] = user.user_id
            pref[this.name] = user.user_name
            pref[this.email] = user.user_email
//            pref[this.image] = user.user_image ?: ""
            pref[this.userAuthProvider] = user.user_auth_provider
            pref[this.userIsNativeRegistration] = user.user_is_native_registration
        }
    }

    override suspend fun getUser(): Flow<User> {
        return dataStore.data.map { pref ->
            val id = pref[this.id]
            val name = pref[this.name]
            val email = pref[this.email]
//            val image = pref[this.image]

            User(
                user_id = id!!,
                user_email = email!!,
                user_image = "",
                user_name = name!!
            )
        }
    }

    override suspend fun setToken(token: String) {
        dataToken.edit { pref ->
            pref[this.token] = token
        }
    }

    override suspend fun getToken(): String {
        return dataToken.data.map { pref ->
            pref[token] ?: ""
        }.first()
    }

    override suspend fun logout(): Boolean {
        return try {
            dataToken.edit { pref ->
                pref.clear()
            }
            true
        } catch (e: Exception) {
            Log.e("LogoutDataStore", "Excep: $e")
            false
        }
    }
}