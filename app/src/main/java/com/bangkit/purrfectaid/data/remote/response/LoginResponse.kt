package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.User

/**
 * Created by Yosua on 12/06/2023
 */
data class LoginResponse(
    val status: Int,
    val msg: String,
    val data: User,
    val token: String
)