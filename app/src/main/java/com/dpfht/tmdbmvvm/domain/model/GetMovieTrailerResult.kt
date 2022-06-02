package com.dpfht.tmdbmvvm.domain.model

import com.dpfht.tmdbmvvm.data.model.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
