package com.bangkit.purrfectaid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.purrfectaid.data.remote.ApiCommunity
import com.bangkit.purrfectaid.domain.model.InsertPostRequest
import com.bangkit.purrfectaid.domain.model.Post
import com.bangkit.purrfectaid.domain.repository.CommunityRepository
import com.bangkit.purrfectaid.utils.Result

/**
 * Created by Yosua on 15/06/2023
 */
class CommunityRepositoryImpl (private val api: ApiCommunity) : CommunityRepository {

    override fun insertPost(insertPostRequest: InsertPostRequest): LiveData<Result<Post>> = liveData {
        emit(Result.Loading)
        try {
            api.insertPost(insertPostRequest).let {
                if (it.isSuccessful) {
                    val body = it.body()!!
                    emit(Result.Success(body.data))
                } else {
                    emit(Result.Error(it.message()))
                }
            }
        } catch (e: Exception) {
            Log.e("INSERTPOST", "ERROR: $e")
            emit(Result.Error(e.message ?: "Terjadi kesalahan pada function Insert Posts"))
        }
    }
}