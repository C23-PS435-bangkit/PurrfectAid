package com.bangkit.purrfectaid.presentation.community

import androidx.lifecycle.ViewModel
import com.bangkit.purrfectaid.domain.model.InsertPostRequest
import com.bangkit.purrfectaid.domain.repository.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(private val repo: CommunityRepository) : ViewModel() {

    fun insertPost(request: InsertPostRequest) = repo.insertPost(request)

    fun getAllPost() = repo.getAllPost()

}