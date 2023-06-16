package com.bangkit.purrfectaid.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yosua on 15/06/2023
 */
data class InsertPost(
    @SerializedName("community_post_id")
    val postId: String,
    @SerializedName("community_post_title")
    val postTitle: String,
    @SerializedName("community_post_content")
    val postContent: String,
    @SerializedName("community_post_user_id")
    val postUserId: Int
)
