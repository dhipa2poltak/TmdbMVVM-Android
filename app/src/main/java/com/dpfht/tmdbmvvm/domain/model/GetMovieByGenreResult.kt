package com.dpfht.tmdbmvvm.domain.model

import com.dpfht.tmdbmvvm.data.model.Movie

data class GetMovieByGenreResult(
  val movies: List<Movie>,
  val page: Int
)
