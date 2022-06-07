package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.model.remote.Genre

interface GetMovieGenreUseCase {

  operator fun invoke(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit)
}
