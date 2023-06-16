package com.bangkit.purrfectaid.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yosua on 16/06/2023
 */
data class LoginGoogleRequest(
    val google_id: Int,
    val google_email: String,
    val google_name: String,
    val google_picture: String
)