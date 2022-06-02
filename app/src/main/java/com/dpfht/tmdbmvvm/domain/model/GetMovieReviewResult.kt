package com.dpfht.tmdbmvvm.domain.model

import com.dpfht.tmdbmvvm.data.model.Review

data class GetMovieReviewResult(
  val reviews: List<Review>,
  val page: Int
)
