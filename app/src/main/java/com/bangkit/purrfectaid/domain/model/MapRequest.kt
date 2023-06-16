package com.bangkit.purrfectaid.domain.model

import android.location.Location
import retrofit2.http.Query

data class MapRequest (
    val location: String,
    val radius: Int,
    val keyword: String,
    val apiKey: String
    )