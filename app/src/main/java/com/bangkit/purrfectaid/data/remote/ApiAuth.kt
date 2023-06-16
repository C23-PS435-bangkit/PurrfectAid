package com.bangkit.purrfectaid.data.remote

import com.bangkit.purrfectaid.data.remote.response.GetDataUserResponse
import com.bangkit.purrfectaid.data.remote.response.LoginResponse
import com.bangkit.purrfectaid.data.remote.response.RegisterResponse
import com.bangkit.purrfectaid.domain.model.LoginGoogleRequest
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.domain.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Yosua on 31/05/2023
 */
interface ApiAuth {

    @POST("users/signin")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("users/signup")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("users/google")
    suspend fun loginGoogle(
        @Body request: LoginGoogleRequest
    ): Response<LoginResponse>

    @GET
    suspend fun getData(): Response<GetDataUserResponse>

}