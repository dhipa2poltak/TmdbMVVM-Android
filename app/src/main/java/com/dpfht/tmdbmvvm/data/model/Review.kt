package com.dpfht.tmdbmvvm.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Suppress("unused")
data class Review(
    var author: String? = null,

    @SerializedName("author_details")
    @Expose
    var authorDetails: AuthorDetails? = null,

    var content: String? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: Date? = null,

    var id: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: Date? = null,

    var url: String? = null
)
