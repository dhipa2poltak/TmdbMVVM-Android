package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.base.BaseUseCase
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import io.reactivex.Observable

interface GetMovieReviewUseCase: BaseUseCase {

  operator fun invoke(movieId: Int, page: Int): Observable<GetMovieReviewResult>
}
