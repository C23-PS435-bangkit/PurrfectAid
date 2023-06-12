package com.bangkit.purrfectaid.utils

import com.google.gson.Gson
import retrofit2.Response

/**
 * Created by Yosua on 12/06/2023
 */
inline fun <reified T> Response<*>.getError() : T {
    return Gson().fromJson(this.errorBody()?.charStream(), T::class.java)
}