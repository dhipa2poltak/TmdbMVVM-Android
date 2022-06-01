package com.dpfht.tmdbmvvm.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Movie(
    var adult: Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null,

    var id: Int = 0,

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,

    var overview: String? = null,
    var popularity: Float = 0.0f,

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,

    var title: String? = null,
    var video: Boolean = false,

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float = 0.0f,

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0
)
