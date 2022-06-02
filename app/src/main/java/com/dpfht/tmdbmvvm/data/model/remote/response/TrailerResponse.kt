package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Trailer

data class TrailerResponse(
    var id: Int = 0,
    var results: ArrayList<Trailer>? = null
)
