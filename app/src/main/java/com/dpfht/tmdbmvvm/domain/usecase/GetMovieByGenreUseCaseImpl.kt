package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.Movie
import com.dpfht.tmdbmvvm.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override operator fun invoke(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMoviesByGenre(genreId.toString(), page).enqueue(object : CallbackWrapper<DiscoverMovieByGenreResponse>() {
      override fun onSuccessCall(responseBody: DiscoverMovieByGenreResponse) {
        responseBody.results?.let {
          onSuccess(it, responseBody.page)
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
