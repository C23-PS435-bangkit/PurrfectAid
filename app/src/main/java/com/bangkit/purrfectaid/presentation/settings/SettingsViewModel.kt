package com.bangkit.purrfectaid.presentation.settings

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStore: DataStoreRepository) : ViewModel() {

    suspend fun logout() = dataStore.logout()
}