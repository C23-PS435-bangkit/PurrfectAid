package com.bangkit.purrfectaid.data.remote.response

/**
 * Created by Yosua on 12/06/2023
 */
data class LoginResponse(
    val status: Int,
    val msg: String,
    val token: String
)