package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.base.BaseUseCase
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import io.reactivex.Observable

interface GetMovieTrailerUseCase: BaseUseCase {

  operator fun invoke(movieId: Int): Observable<GetMovieTrailerResult>
}
