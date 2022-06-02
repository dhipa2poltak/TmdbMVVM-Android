package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class DiscoverMovieByGenreResponse(
    val page: Int = 0,
    val results: List<Movie>? = null,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0
)
