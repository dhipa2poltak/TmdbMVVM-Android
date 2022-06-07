package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.model.remote.response.MovieDetailsResponse

interface GetMovieDetailsUseCase {

  operator fun invoke(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  )
}
