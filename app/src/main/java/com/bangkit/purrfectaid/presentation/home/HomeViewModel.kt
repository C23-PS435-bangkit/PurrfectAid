package com.bangkit.purrfectaid.presentation.home

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.repository.PredictRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: PredictRepository) : ViewModel() {

    fun getDiagnoseHistory() = repo.getDiagnose()
}