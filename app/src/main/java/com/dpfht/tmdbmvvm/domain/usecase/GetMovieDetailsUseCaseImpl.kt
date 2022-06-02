package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import io.reactivex.Observable

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override operator fun invoke(movieId: Int): Observable<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
