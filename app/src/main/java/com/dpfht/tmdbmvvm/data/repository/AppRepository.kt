package com.dpfht.tmdbmvvm.data.repository

import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper

interface AppRepository {

  suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult>

  suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult>

  suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult>

  suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult>

  suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult>
}
