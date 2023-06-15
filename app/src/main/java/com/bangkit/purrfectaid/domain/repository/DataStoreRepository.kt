package com.bangkit.purrfectaid.domain.repository

import com.bangkit.purrfectaid.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Yosua on 12/06/2023
 */
interface DataStoreRepository {
    suspend fun setUser(user: User)
    suspend fun getUser() : Flow<User>
    suspend fun setToken(token: String)
    suspend fun getToken() : String?
    suspend fun logout() : Boolean
}