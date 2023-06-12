package com.bangkit.purrfectaid.presentation.auth

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun login(loginRequest: LoginRequest) = repo.login(loginRequest)
}