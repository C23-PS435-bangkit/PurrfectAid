package com.bangkit.purrfectaid.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Created by Yosua on 14/06/2023
 */
fun File.toImageMultiPart(): MultipartBody.Part {
    val requestImageFile = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", this.name, requestImageFile)
}