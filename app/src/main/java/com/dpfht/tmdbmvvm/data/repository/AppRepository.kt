package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.model.remote.response.TrailerResponse
import io.reactivex.Observable

interface AppRepository {

  fun getMovieGenre():  Observable<GenreResponse>

  fun getMoviesByGenre(genreId: String, page: Int): Observable<DiscoverMovieByGenreResponse>

  fun getMovieDetail(movieId: Int): Observable<MovieDetailsResponse>

  fun getMovieReviews(movieId: Int, page: Int): Observable<ReviewResponse>

  fun getMovieTrailer(movieId: Int): Observable<TrailerResponse>
}
