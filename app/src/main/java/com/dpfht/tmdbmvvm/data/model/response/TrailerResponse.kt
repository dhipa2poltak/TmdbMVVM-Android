package com.dpfht.tmdbmvvm.data.model.response

import com.dpfht.tmdbmvvm.data.model.Trailer

data class TrailerResponse(
    var id: Int = 0,
    var results: ArrayList<Trailer>? = null
)
