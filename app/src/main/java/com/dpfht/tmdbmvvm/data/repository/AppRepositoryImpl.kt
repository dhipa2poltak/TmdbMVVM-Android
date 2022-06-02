package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.data.api.RestService
import com.dpfht.tmdbmvvm.data.api.ResultWrapper
import com.dpfht.tmdbmvvm.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.response.TrailerResponse
import com.dpfht.tmdbmvvm.util.safeApiCall
import kotlinx.coroutines.Dispatchers

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override suspend fun getMovieGenre():  ResultWrapper<GenreResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): ResultWrapper<DiscoverMovieByGenreResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }
  }

  override suspend fun getMovieDetail(movieId: Int): ResultWrapper<MovieDetailsResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ResultWrapper<ReviewResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }
  }

  override suspend fun getMovieTrailer(movieId: Int): ResultWrapper<TrailerResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }
  }
}
