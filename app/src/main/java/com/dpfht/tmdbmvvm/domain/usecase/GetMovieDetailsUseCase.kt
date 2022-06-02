package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult>
}
