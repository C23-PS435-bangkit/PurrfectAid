package com.bangkit.purrfectaid.domain.model

data class MapRequest (
    val location: String,
    val radius: Int,
    val keyword: String,
    val apiKey: String
    )