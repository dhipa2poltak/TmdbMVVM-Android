package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.data.api.ResultWrapper
import com.dpfht.tmdbmvvm.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.response.TrailerResponse

interface AppRepository {

  suspend fun getMovieGenre():  ResultWrapper<GenreResponse>

  suspend fun getMoviesByGenre(genreId: String, page: Int): ResultWrapper<DiscoverMovieByGenreResponse>

  suspend fun getMovieDetail(movieId: Int): ResultWrapper<MovieDetailsResponse>

  suspend fun getMovieReviews(movieId: Int, page: Int): ResultWrapper<ReviewResponse>

  suspend fun getMovieTrailer(movieId: Int): ResultWrapper<TrailerResponse>
}
