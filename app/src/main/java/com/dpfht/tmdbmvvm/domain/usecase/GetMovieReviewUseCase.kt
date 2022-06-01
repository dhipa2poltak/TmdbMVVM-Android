package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.model.Review

interface GetMovieReviewUseCase {

  operator fun invoke(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  )
}
