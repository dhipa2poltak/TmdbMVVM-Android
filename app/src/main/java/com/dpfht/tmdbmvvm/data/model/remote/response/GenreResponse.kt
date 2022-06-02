package com.dpfht.tmdbmvvm.data.model.remote.response

import com.dpfht.tmdbmvvm.data.model.remote.Genre

data class GenreResponse(
    var genres: List<Genre>? = null
)
