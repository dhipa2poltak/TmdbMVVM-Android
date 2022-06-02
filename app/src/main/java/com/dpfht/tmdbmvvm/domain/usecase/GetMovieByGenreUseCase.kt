package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult

interface GetMovieByGenreUseCase {

  suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult>
}
