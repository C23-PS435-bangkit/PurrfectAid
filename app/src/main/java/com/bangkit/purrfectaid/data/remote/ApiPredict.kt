package com.bangkit.purrfectaid.data.remote

import com.bangkit.purrfectaid.domain.model.Predict
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Yosua on 13/06/2023
 */
interface ApiPredict {

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part images: MultipartBody.Part
    ) : Response<Predict>
}