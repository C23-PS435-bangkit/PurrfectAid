package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.InsertPost

/**
 * Created by Yosua on 15/06/2023
 */
data class PostResponse(
    val status: Int,
    val msg: String,
    val data: InsertPost
)
