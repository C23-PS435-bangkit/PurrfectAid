package com.bangkit.purrfectaid.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.purrfectaid.data.remote.response.MapResponse
import com.bangkit.purrfectaid.domain.model.MapRequest
import com.bangkit.purrfectaid.utils.Result

interface MapRepository {

    fun getLocation(request: MapRequest): LiveData<Result<MapResponse>>
}