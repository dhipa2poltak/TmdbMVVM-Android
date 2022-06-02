package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
