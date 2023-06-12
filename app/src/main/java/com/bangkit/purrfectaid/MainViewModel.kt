package com.bangkit.purrfectaid

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Yosua on 13/06/2023
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val dataStore: DataStoreRepository): ViewModel() {

    suspend fun getToken() = dataStore.getToken()
}