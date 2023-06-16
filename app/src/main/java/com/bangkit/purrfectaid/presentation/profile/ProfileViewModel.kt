package com.bangkit.purrfectaid.presentation.profile

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dataStore: DataStoreRepository) : ViewModel() {


}