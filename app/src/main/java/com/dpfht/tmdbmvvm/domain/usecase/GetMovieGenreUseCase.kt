package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult>
}
