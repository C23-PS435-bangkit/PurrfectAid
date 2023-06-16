package com.bangkit.purrfectaid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.purrfectaid.data.remote.ApiAuth
import com.bangkit.purrfectaid.data.remote.response.ErrorResponse
import com.bangkit.purrfectaid.data.remote.response.LoginResponse
import com.bangkit.purrfectaid.data.remote.response.RegisterResponse
import com.bangkit.purrfectaid.domain.model.LoginGoogleRequest
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.domain.model.RegisterRequest
import com.bangkit.purrfectaid.domain.model.User
import com.bangkit.purrfectaid.domain.model.UserGoogle
import com.bangkit.purrfectaid.domain.repository.AuthRepository
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import com.bangkit.purrfectaid.utils.Result
import com.bangkit.purrfectaid.utils.getError

/**
 * Created by Yosua on 12/06/2023
 */
class AuthRepositoryImpl (private val apiAuth: ApiAuth, private val dataStore: DataStoreRepository) : AuthRepository {

    override fun login(request: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiAuth.login(request).let {
                if (it.isSuccessful) {
                    val body = it.body()!!
                    dataStore.setToken(body.token)
                    dataStore.setUser(body.data)
                    emit(Result.Success(body))
                } else {
                    val errorMessage = it.getError<ErrorResponse>().msg
                    emit(Result.Error(errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada login"))
        }
    }

    override fun register(request: RegisterRequest): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiAuth.register(request).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Result.Success(body!!))
                } else {
                    val errorMessage = it.getError<ErrorResponse>().msg
                    emit(Result.Error(errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada register"))
        }
    }

    override fun loginGoogle(request: LoginGoogleRequest): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            apiAuth.loginGoogle(request).let {
                if (it.isSuccessful) {
                    val body = it.body()!!
                    dataStore.setToken(body.token)
                    dataStore.setUser(body.data)
                    emit(Result.Success(body.data))
                } else {
                    emit(Result.Error(it.body()!!.msg))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada saat login google"))
        }
    }


    override suspend fun logout(): Boolean {
        return dataStore.logout()
    }
}