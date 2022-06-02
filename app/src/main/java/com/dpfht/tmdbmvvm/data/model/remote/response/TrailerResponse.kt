package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Trailer

data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)

