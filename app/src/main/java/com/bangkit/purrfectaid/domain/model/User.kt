package com.bangkit.purrfectaid.domain.model

/**
 * Created by Yosua on 31/05/2023
 */
data class User(
    val user_id: Int,
    val user_email: String,
    val user_name: String,
    val user_image: String? = null,
    val user_auth_provider: Int = 4,
    val user_is_native_registration: Int = 1,
)
