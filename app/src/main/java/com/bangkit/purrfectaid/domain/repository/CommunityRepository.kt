package com.bangkit.purrfectaid.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.purrfectaid.domain.model.InsertPostRequest
import com.bangkit.purrfectaid.domain.model.Post
import com.bangkit.purrfectaid.utils.Result

/**
 * Created by Yosua on 15/06/2023
 */
interface CommunityRepository {

    fun insertPost(insertPostRequest: InsertPostRequest) : LiveData<Result<InsertPostRequest>>
    fun getAllPost() : LiveData<Result<List<Post>>>
}