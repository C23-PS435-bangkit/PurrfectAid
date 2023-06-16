package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.Predict

/**
 * Created by Yosua on 16/06/2023
 */
data class PredictResponse(
    val message: String,
    val data: Predict?
)
