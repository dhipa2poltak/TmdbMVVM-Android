package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import io.reactivex.Observable

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override operator fun invoke(genreId: Int, page: Int): Observable<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
