package com.bangkit.purrfectaid.domain.model

/**
 * Created by Yosua on 16/06/2023
 */
data class LoginGoogleRequest(
    val id: Int,
    val email: String,
    val name: String,
    val pictureUrl: String
)