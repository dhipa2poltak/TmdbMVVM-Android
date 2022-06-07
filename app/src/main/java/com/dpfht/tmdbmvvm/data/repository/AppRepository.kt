package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.TrailerResponse
import retrofit2.Call

interface AppRepository {

  fun getMovieGenre():  Call<GenreResponse>

  fun getMoviesByGenre(genreId: String, page: Int): Call<DiscoverMovieByGenreResponse>

  fun getMovieDetail(movieId: Int): Call<MovieDetailsResponse>

  fun getMovieReviews(movieId: Int, page: Int): Call<ReviewResponse>

  fun getMovieTrailer(movieId: Int): Call<TrailerResponse>
}
