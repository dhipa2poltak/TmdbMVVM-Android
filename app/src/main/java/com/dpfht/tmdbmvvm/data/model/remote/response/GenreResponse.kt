package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Genre
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult

data class GenreResponse(
    val genres: List<Genre>? = null
)

fun GenreResponse.toDomain() = GetMovieGenreResult(genres ?: arrayListOf())
