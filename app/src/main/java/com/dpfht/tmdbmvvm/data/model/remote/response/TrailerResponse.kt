package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Trailer
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult

data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)

fun TrailerResponse.toDomain() = GetMovieTrailerResult(this.results ?: arrayListOf())
