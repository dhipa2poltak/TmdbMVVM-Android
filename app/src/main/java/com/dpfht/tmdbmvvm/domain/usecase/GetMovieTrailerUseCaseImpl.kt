package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import io.reactivex.Observable

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override operator fun invoke(movieId: Int): Observable<GetMovieTrailerResult> {
    return appRepository.getMovieTrailer(movieId)
  }
}
