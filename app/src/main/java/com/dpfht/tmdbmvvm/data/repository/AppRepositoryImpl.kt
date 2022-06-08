package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.data.api.RestService
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvvm.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvvm.data.model.remote.response.toDomain
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbmvvm.util.safeApiCall
import kotlinx.coroutines.Dispatchers

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override suspend fun getMovieGenre():  UseCaseResultWrapper<GetMovieGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
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
