package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.model.remote.Trailer

interface GetMovieTrailerUseCase {

  operator fun invoke(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  )
}
