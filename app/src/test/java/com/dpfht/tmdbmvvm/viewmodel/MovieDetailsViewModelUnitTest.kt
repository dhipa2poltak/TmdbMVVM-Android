package com.dpfht.tmdbmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbmvvm.Config
import com.dpfht.tmdbmvvm.RxImmediateSchedulerRule
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbmvvm.feature.moviedetails.MovieDetailsViewModel
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
class MovieDetailsViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var viewModel: MovieDetailsViewModel

  @Mock
  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  private val compositeDisposable = CompositeDisposable()

  @Mock
  private lateinit var titleObserver: Observer<String>

  @Mock
  private lateinit var overviewObserver: Observer<String>

  @Mock
  private lateinit var imageUrlObserver: Observer<String>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, compositeDisposable)
  }

  @Test
  fun `fetch movie details successfully`() {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = GetMovieDetailsResult(
      movieId = movieId,
      title = title,
      overview = overview,
      posterPath = posterPath
    )

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(Observable.just(getMovieDetailsResult))

    viewModel.titleData.observeForever(titleObserver)
    viewModel.overviewData.observeForever(overviewObserver)
    viewModel.imageUrlData.observeForever(imageUrlObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(titleObserver).onChanged(eq(title))
    verify(overviewObserver).onChanged(eq(overview))

    val imageUrl = Config.IMAGE_URL_BASE_PATH + posterPath
    verify(imageUrlObserver).onChanged(eq(imageUrl))

    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie details`() {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = GetMovieDetailsResult(
      movieId = movieId,
      title = title,
      overview = overview,
      posterPath = posterPath
    )

    val msg = "error in conversion"

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(
      Observable.just(getMovieDetailsResult)
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