package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.model.Movie

interface GetMovieByGenreUseCase {

  operator fun invoke(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  )
}
