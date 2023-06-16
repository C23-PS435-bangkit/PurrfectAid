package com.bangkit.purrfectaid.data.remote.response

import com.bangkit.purrfectaid.domain.model.Diagnose

/**
 * Created by Yosua on 16/06/2023
 */
data class DiagnoseHistoryResponse(
    val status: Int,
    val msg: String?,
    val data: List<Diagnose>?
)
