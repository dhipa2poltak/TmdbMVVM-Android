package com.dpfht.tmdbmvvm.data.api

import com.dpfht.tmdbmvvm.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.response.TrailerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

  @GET("genre/movie/list")
  fun getMovieGenre():  Observable<GenreResponse>

  @GET("discover/movie")
  fun getMoviesByGenre(
    @Query("with_genres") genreId: String,
    @Query("page") page: Int): Observable<DiscoverMovieByGenreResponse>

  @GET("movie/{movie_id}")
  fun getMovieDetail(
    @Path("movie_id") movieId: Int): Observable<MovieDetailsResponse>

  @GET("movie/{movie_id}/reviews")
  fun getMovieReviews(
    @Path("movie_id") movieId: Int,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"): Observable<ReviewResponse>

  @GET("movie/{movie_id}/videos")
  fun getMovieTrailers(
    @Path("movie_id") movieId: Int,
    @Query("language") language: String = "en-US"): Observable<TrailerResponse>
}
