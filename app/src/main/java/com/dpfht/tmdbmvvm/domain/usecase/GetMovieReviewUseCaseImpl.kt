package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieReviewResult> {
    return when (val response = appRepository.getMovieReviews(movieId, page)) {
      is Success -> {
        val reviews = response.value.results ?: arrayListOf()
        UseCaseResultWrapper.Success(GetMovieReviewResult(reviews, response.value.page))
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
