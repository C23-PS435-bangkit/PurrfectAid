package com.bangkit.purrfectaid.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yosua on 16/06/2023
 */
data class Diagnose(
    @SerializedName("scan_id")
    val scanId: String,
    @SerializedName("scan_pict")
    val scanPict: String,
    @SerializedName("scan_diagnose")
    val scanDiagnose: String,
    @SerializedName("scan_treatment")
    val scanTreatment: String,
    @SerializedName("scan_date")
    val scanDate: String
)
