package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.UserGoogle

/**
 * Created by Yosua on 16/06/2023
 */
data class GetDataUserResponse(
    val status: Int,
    val data: UserGoogle,
    val msg: String?
)