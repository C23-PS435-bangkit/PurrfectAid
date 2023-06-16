package com.bangkit.purrfectaid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.purrfectaid.data.remote.ApiPredict
import com.bangkit.purrfectaid.domain.model.Diagnose
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
                val body = it.body()!!
                if (it.isSuccessful) {
                    emit(Result.Success(body.data!!))
                } else {
                    emit(Result.Error(body.message))
                }
            }
        } catch (e: Exception) {
            Log.e("ERRUR", e.message + " dan dan $e")
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada predict"))
        }

    }

    override fun getDiagnose(): LiveData<Result<List<Diagnose>>> = liveData {
        emit(Result.Loading)
        try {
            api.getDiagnose().let {
                val body = it.body()!!
                if (it.isSuccessful) {
                    if (body.data != null) {
                        emit(Result.Success(body.data))
                    } else {
                        emit(Result.Success(emptyList()))
                    }
                } else {
                    emit(Result.Error(body.msg!!))
                }
            }
        } catch (e: Exception) {
            Log.e("GetDiagnose", e.message ?: "dan $e")
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada getDiagnose"))
        }

    }
}