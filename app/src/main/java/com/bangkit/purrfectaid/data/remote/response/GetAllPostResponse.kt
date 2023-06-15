package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.Post

/**
 * Created by Yosua on 15/06/2023
 */
data class GetAllPostResponse(
    val status: Int,
    val data: List<Post>
)
