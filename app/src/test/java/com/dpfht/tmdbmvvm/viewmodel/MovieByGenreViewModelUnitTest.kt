package com.dpfht.tmdbmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbmvvm.RxImmediateSchedulerRule
import com.dpfht.tmdbmvvm.data.model.remote.Movie
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbmvvm.feature.moviesbygenre.MoviesByGenreViewModel
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
class MovieByGenreViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var viewModel: MoviesByGenreViewModel

  @Mock
  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  private val listOfMovie = arrayListOf<Movie>()
  private val compositeDisposable = CompositeDisposable()

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = MoviesByGenreViewModel(getMovieByGenreUseCase, listOfMovie, compositeDisposable)
  }

  @Test
  fun `fetch movie successfully`() {
    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = GetMovieByGenreResult(movies, page)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(Observable.just(getMovieByGenreResult))

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfMovie.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie`() {
    val msg = "error in conversion"

    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = GetMovieByGenreResult(movies, page)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(
      Observable.just(getMovieByGenreResult)
        .map { throw Exception(msg) }
    )

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
