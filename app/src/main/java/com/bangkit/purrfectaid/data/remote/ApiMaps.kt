package com.bangkit.purrfectaid.data.remote

import com.bangkit.purrfectaid.data.remote.response.MapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMaps {

    @GET("json")
    suspend fun getLocation(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): Response<MapResponse>


}