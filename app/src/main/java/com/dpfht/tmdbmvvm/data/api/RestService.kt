package com.dpfht.tmdbmvvm.data.api

import com.dpfht.tmdbmvvm.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

  @GET("genre/movie/list")
  fun getMovieGenre():  Call<GenreResponse>

  @GET("discover/movie")
  fun getMoviesByGenre(
    @Query("with_genres") genreId: String,
    @Query("page") page: Int): Call<DiscoverMovieByGenreResponse>

  @GET("movie/{movie_id}")
  fun getMovieDetail(
    @Path("movie_id") movieId: Int): Call<MovieDetailsResponse>

  @GET("movie/{movie_id}/reviews")
  fun getMovieReviews(
    @Path("movie_id") movieId: Int,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"): Call<ReviewResponse>

  @GET("movie/{movie_id}/videos")
  fun getMovieTrailers(
    @Path("movie_id") movieId: Int,
    @Query("language") language: String = "en-US"): Call<TrailerResponse>
}
