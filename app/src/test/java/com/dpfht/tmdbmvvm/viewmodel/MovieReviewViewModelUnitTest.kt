package com.dpfht.tmdbmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbmvvm.RxImmediateSchedulerRule
import com.dpfht.tmdbmvvm.data.model.remote.Review
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbmvvm.feature.moviereviews.MovieReviewsViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MovieReviewViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var viewModel: MovieReviewsViewModel

  @Mock
  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  private val listOfReview = arrayListOf<Review>()
  private val compositeDisposable = CompositeDisposable()

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = MovieReviewsViewModel(getMovieReviewUseCase, listOfReview, compositeDisposable)
  }

  @Test
  fun `fetch movie review successfully`() {
    val review1 = Review(author = "author1", content = "content1", id = "1",)
    val review2 = Review(author = "author2", content = "content2", id = "2",)
    val review3 = Review(author = "author3", content = "content3", id = "3",)

    val reviews = listOf(review1, review2, review3)

    val movieId = 1
    val page = 1

    val getMovieReviewResult = GetMovieReviewResult(reviews, page)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(Observable.just(getMovieReviewResult))

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfReview.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie review`() {
    val msg = "error in conversion"

    val review1 = Review(author = "author1", content = "content1", id = "1",)
    val review2 = Review(author = "author2", content = "content2", id = "2",)
    val review3 = Review(author = "author3", content = "content3", id = "3",)

    val reviews = listOf(review1, review2, review3)

    val movieId = 1
    val page = 1

    val getMovieReviewResult = GetMovieReviewResult(reviews, page)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(
      Observable.just(getMovieReviewResult)
        .map { throw Exception(msg) }
    )

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
