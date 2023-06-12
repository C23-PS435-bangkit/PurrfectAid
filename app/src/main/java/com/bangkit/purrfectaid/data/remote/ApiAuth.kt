package com.bangkit.purrfectaid.data.remote

import com.bangkit.purrfectaid.data.remote.response.LoginResponse
import com.bangkit.purrfectaid.domain.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Yosua on 31/05/2023
 */
interface ApiAuth {

    @POST("users/signin")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}