package com.dpfht.tmdbmvvm.data.model.response

import com.dpfht.tmdbmvvm.data.model.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class DiscoverMovieByGenreResponse(
    var page: Int = 0,
    var results: List<Movie>? = null,

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0,

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0
)
