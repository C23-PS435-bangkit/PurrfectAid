package com.bangkit.purrfectaid.presentation.scan

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.repository.PredictRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(private val repo: PredictRepository) : ViewModel() {

    fun predict(image: MultipartBody.Part) = repo.predict(image)
    // TODO: Implement the ViewModel
}