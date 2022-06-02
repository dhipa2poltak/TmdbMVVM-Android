package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper.ErrorResult

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult> {
    return when (val response = appRepository.getMovieDetail(movieId)) {
      is Success -> {
        UseCaseResultWrapper.Success(GetMovieDetailsResult(
          response.value.id,
          response.value.title ?: "",
          response.value.overview ?: "",
          response.value.posterPath ?: ""
        ))
      }
      is GenericError -> {
        if (response.code != null && response.error != null) {
          ErrorResult(response.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }
}
