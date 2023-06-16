package com.bangkit.purrfectaid.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.purrfectaid.domain.model.Diagnose
import com.bangkit.purrfectaid.domain.model.Predict
import com.bangkit.purrfectaid.utils.Result
import okhttp3.MultipartBody

/**
 * Created by Yosua on 13/06/2023
 */
interface PredictRepository {

    fun predict(image: MultipartBody.Part) : LiveData<Result<Predict>>

    fun getDiagnose() : LiveData<Result<List<Diagnose>>>
}