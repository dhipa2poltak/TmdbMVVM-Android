package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.base.BaseUseCase
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import io.reactivex.Observable

interface GetMovieDetailsUseCase: BaseUseCase {

  operator fun invoke(movieId: Int): Observable<GetMovieDetailsResult>
}
