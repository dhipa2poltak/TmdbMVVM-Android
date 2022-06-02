package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import io.reactivex.Observable

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override operator fun invoke(): Observable<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
