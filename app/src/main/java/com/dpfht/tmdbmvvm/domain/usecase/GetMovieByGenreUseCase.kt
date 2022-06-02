package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.base.BaseUseCase
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import io.reactivex.Observable

interface GetMovieByGenreUseCase: BaseUseCase {

  operator fun invoke(genreId: Int, page: Int): Observable<GetMovieByGenreResult>
}
