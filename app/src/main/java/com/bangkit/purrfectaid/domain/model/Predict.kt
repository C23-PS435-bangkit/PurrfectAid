package com.bangkit.purrfectaid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Yosua on 13/06/2023
 */

@Parcelize
data class Predict(
    val diagnose: String,
    val treatment: String
) : Parcelable