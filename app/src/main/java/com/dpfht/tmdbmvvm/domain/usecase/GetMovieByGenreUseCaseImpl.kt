package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
