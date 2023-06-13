package com.bangkit.purrfectaid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.purrfectaid.data.remote.ApiPredict
import com.bangkit.purrfectaid.domain.model.Predict
import com.bangkit.purrfectaid.domain.repository.PredictRepository
import com.bangkit.purrfectaid.utils.Result
import okhttp3.MultipartBody

/**
 * Created by Yosua on 13/06/2023
 */
class PredictRepositoryImpl (private val api: ApiPredict) : PredictRepository {

    override fun predict(image: MultipartBody.Part): LiveData<Result<Predict>> = liveData {
        emit(Result.Loading)
        try {
            api.predict(image).let {
                if (it.isSuccessful) {
                    val body = it.body()!!
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error(it.errorBody().toString()))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada predict"))
        }

    }
}