package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult> {
    return when (val response = appRepository.getMoviesByGenre(genreId.toString(), page)) {
      is Success -> {
        val movies = response.value.results ?: arrayListOf()
        UseCaseResultWrapper.Success(GetMovieByGenreResult(movies, response.value.page)
        )
      }
      is GenericError -> {
        if (response.code != null && response.error != null) {
          UseCaseResultWrapper.ErrorResult(response.error.statusMessage ?: "")
        } else {
          UseCaseResultWrapper.ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        UseCaseResultWrapper.ErrorResult("error in connection")
      }
    }
  }
}
