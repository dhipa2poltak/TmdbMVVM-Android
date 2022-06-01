package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.Genre
import com.dpfht.tmdbmvvm.data.model.response.GenreResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override operator fun invoke(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieGenre().enqueue(object : CallbackWrapper<GenreResponse?>() {
      override fun onSuccessCall(responseBody: GenreResponse?) {
        responseBody?.genres?.let { genres ->
          onSuccess(genres)
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
