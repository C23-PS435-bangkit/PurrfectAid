package com.bangkit.purrfectaid.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.purrfectaid.data.remote.response.LoginResponse
import com.bangkit.purrfectaid.data.remote.response.RegisterResponse
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.domain.model.RegisterRequest
import com.bangkit.purrfectaid.utils.Result

/**
 * Created by Yosua on 12/06/2023
 */
interface AuthRepository {

    fun login(request: LoginRequest) : LiveData<Result<LoginResponse>>
    fun register(request: RegisterRequest) : LiveData<Result<RegisterResponse>>
    suspend fun logout() : Boolean
    fun registerOrLoginWithGoogle() : LiveData<Result<LoginResponse>>
}