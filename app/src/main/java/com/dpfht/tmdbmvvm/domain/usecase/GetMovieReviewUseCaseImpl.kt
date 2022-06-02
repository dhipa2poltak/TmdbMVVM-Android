package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import io.reactivex.Observable

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override operator fun invoke(movieId: Int, page: Int): Observable<GetMovieReviewResult> {
    return appRepository.getMovieReviews(movieId, page)

  }
}
