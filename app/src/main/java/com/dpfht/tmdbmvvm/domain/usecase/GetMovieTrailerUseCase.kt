package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult>
}
