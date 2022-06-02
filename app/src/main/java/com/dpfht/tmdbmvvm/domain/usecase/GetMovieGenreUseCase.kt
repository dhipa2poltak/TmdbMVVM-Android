package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.base.BaseUseCase
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import io.reactivex.Observable

interface GetMovieGenreUseCase: BaseUseCase {

  operator fun invoke(): Observable<GetMovieGenreResult>
}
