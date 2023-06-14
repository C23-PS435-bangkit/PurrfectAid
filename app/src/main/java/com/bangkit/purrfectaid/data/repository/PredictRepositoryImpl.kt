package com.bangkit.purrfectaid.data.repository

import android.util.Log
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
                    Log.d("DATASA", it.message())
                    val body = it.body().toString().replace("\"", "")
                    Log.d("DATADATA", body.toString())
                    emit(Result.Success(it.body()!!))
                } else {
                    Log.e("ERROR PREDICTSI", it.message() + it.errorBody())
                    emit(Result.Error(it.message()))
                }
            }
        } catch (e: Exception) {
            Log.e("ERRUR", e.message + " dan dan $e")
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada predict"))
        }

    }
}