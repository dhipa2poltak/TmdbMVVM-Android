package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override operator fun invoke(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieDetail(movieId).enqueue(object : CallbackWrapper<MovieDetailsResponse>() {
      override fun onSuccessCall(responseBody: MovieDetailsResponse) {
        onSuccess(responseBody)
      }

      override fun onErrorCall(message: String) {
        onError(message)
      }

      override fun onCancelCall() {
        onCancel()
      }
    })
  }
}
