package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.Trailer
import com.dpfht.tmdbmvvm.data.model.response.TrailerResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override operator fun invoke(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieTrailer(movieId).enqueue(object : CallbackWrapper<TrailerResponse>() {
      override fun onSuccessCall(responseBody: TrailerResponse) {
        responseBody.results?.let {
          onSuccess(it)
        }
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
