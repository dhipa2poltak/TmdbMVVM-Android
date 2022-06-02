package com.dpfht.tmdbmvvm.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class AuthorDetails(
    var name: String? = null,
    var username: String? = null,

    @SerializedName("avatar_path")
    @Expose
    var avatarPath: String? = null,

    var rating: Float = 0.0f
)
