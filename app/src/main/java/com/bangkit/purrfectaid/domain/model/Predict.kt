package com.bangkit.purrfectaid.domain.model

/**
 * Created by Yosua on 13/06/2023
 */
data class Predict(
    val model_allergic: Float,
    val model_bacterial: Float,
    val model_fungal: Float,
    val model_healthy: Float
)