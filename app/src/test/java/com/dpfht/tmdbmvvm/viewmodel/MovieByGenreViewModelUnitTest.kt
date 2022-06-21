package com.dpfht.tmdbmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbmvvm.MainCoroutineRule
import com.dpfht.tmdbmvvm.data.model.remote.Movie
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper
import com.dpfht.tmdbmvvm.feature.moviesbygenre.MoviesByGenreViewModel
import kotlinx.coroutines.runBlocking
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
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MoviesByGenreViewModel

  @Mock
  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val listOfMovie = arrayListOf<Movie>()

  @Before
  fun setup() {
    viewModel = MoviesByGenreViewModel(getMovieByGenreUseCase, listOfMovie)
  }

  @Test
  fun `fetch movie successfully`() = runBlocking {
    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = GetMovieByGenreResult(movies, page)
    val result = UseCaseResultWrapper.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfMovie.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie`() = runBlocking {
    val msg = "error fetch movie"
    val result = UseCaseResultWrapper.ErrorResult(msg)

    val genreId = 1
    val page = 1

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}