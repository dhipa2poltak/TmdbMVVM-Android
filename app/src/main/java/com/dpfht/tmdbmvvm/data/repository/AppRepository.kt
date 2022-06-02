package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import io.reactivex.Observable

interface AppRepository {

  fun getMovieGenre():  Observable<GetMovieGenreResult>

  fun getMoviesByGenre(genreId: String, page: Int): Observable<GetMovieByGenreResult>

  fun getMovieDetail(movieId: Int): Observable<GetMovieDetailsResult>

  fun getMovieReviews(movieId: Int, page: Int): Observable<GetMovieReviewResult>

  fun getMovieTrailer(movieId: Int): Observable<GetMovieTrailerResult>
}
