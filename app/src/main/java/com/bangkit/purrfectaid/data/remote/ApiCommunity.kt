package com.bangkit.purrfectaid.data.remote

import com.bangkit.purrfectaid.data.remote.response.PostResponse
import com.bangkit.purrfectaid.domain.model.InsertPostRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Yosua on 15/06/2023
 */
interface ApiCommunity {

    @POST("communities")
    suspend fun insertPost(
        @Body request: InsertPostRequest
    ) : Response<PostResponse>
}