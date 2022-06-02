package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult> {
    return when (val response = appRepository.getMovieTrailer(movieId)) {
      is Success -> {
        val trailers = response.value.results ?: arrayListOf()
        UseCaseResultWrapper.Success(GetMovieTrailerResult(trailers))
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
