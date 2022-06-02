package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieReviewResult>
}
