package com.bangkit.purrfectaid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.purrfectaid.data.remote.ApiMaps
import com.bangkit.purrfectaid.data.remote.response.ErrorResponse
import com.bangkit.purrfectaid.data.remote.response.MapResponse
import com.bangkit.purrfectaid.domain.model.MapRequest
import com.bangkit.purrfectaid.domain.repository.MapRepository
import com.bangkit.purrfectaid.utils.Result
import com.bangkit.purrfectaid.utils.getError

class MapRepositoryImpl(private val apiMaps: ApiMaps) :MapRepository {
    override fun getLocation(request: MapRequest): LiveData<Result<MapResponse>> =
        liveData {
            emit(Result.Loading)
            try {
            apiMaps.getLocation(request.location, request.radius, request.keyword, request.apiKey ).let {
                if (it.isSuccessful) {
                    val body = it.body()!!
                    emit(Result.Success(body))
                } else {
                    val errorMessage = it.getError<ErrorResponse>().msg
                    emit(Result.Error(errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada pengambilan data map"))
        }
    }
}