package com.dpfht.tmdbmvvm.domain.model

import com.dpfht.tmdbmvvm.data.model.remote.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
