package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
