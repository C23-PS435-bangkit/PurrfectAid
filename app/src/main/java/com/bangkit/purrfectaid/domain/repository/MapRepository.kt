package com.bangkit.purrfectaid.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.purrfectaid.data.remote.response.MapResponse
import com.bangkit.purrfectaid.domain.model.MapRequest
import com.bangkit.purrfectaid.utils.Result
import okhttp3.ResponseBody

interface MapRepository {

    fun getLocation(request: MapRequest): LiveData<Result<MapResponse>>
    fun getImage(photo_reference: String, api_key: String): LiveData<Result<ResponseBody>>
}