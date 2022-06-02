package com.dpfht.tmdbmvvm.data.api

import com.dpfht.tmdbmvvm.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.response.TrailerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

  @GET("genre/movie/list")
  suspend fun getMovieGenre():  GenreResponse

  @GET("discover/movie")
  suspend fun getMoviesByGenre(
    @Query("with_genres") genreId: String,
    @Query("page") page: Int): DiscoverMovieByGenreResponse

  @GET("movie/{movie_id}")
  suspend fun getMovieDetail(
    @Path("movie_id") movieId: Int): MovieDetailsResponse

  @GET("movie/{movie_id}/reviews")
  suspend fun getMovieReviews(
    @Path("movie_id") movieId: Int,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"): ReviewResponse

  @GET("movie/{movie_id}/videos")
  suspend fun getMovieTrailers(
    @Path("movie_id") movieId: Int,
    @Query("language") language: String = "en-US"): TrailerResponse
}
