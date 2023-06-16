package com.bangkit.purrfectaid.presentation.vet

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.model.MapRequest
import com.bangkit.purrfectaid.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VetViewModel  @Inject constructor(private val repo: MapRepository) : ViewModel() {

   fun getLocation(request: MapRequest) = repo.getLocation(request)

   fun getImage(photo_reference: String, api_key: String) = repo.getImage(photo_reference, api_key)

}