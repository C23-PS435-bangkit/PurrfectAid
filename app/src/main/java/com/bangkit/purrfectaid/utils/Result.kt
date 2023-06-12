package com.bangkit.purrfectaid.utils

/**
 * Created by Yosua on 12/06/2023
 */
sealed class Result<out T> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
