package com.bangkit.purrfectaid.domain.model

/**
 * Created by Yosua on 31/05/2023
 */
data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)
