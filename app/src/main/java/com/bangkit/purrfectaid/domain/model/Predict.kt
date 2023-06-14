package com.bangkit.purrfectaid.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yosua on 13/06/2023
 */
data class Predict(
    @SerializedName("model_allergic")
    val modelAllergic: Float,
    @SerializedName("model_bacterial")
    val modelBacterial: Float,
    @SerializedName("model_fungal")
    val modelFungal: Float,
    @SerializedName("model_healthy")
    val modelHealthy: Float
)