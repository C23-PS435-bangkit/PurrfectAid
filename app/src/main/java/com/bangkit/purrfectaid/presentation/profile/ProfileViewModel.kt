package com.bangkit.purrfectaid.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.purrfectaid.domain.model.User
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dataStore: DataStoreRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUser() {
        viewModelScope.launch {
            dataStore.getUser().collect {
                _user.value = it
            }
        }
    }

    suspend fun logout() = dataStore.logout()

}