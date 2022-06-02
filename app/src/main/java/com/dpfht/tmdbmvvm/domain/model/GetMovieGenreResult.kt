package com.dpfht.tmdbmvvm.domain.model

import com.dpfht.tmdbmvvm.data.model.Genre

data class GetMovieGenreResult(
  val genres: List<Genre>
)
