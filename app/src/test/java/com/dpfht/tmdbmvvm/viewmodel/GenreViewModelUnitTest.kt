package com.dpfht.tmdbmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.testnews.MainCoroutineRule
import com.dpfht.tmdbmvvm.data.model.remote.Genre
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbmvvm.domain.usecase.UseCaseResultWrapper
import com.dpfht.tmdbmvvm.feature.genre.GenreViewModel
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
class GenreViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: GenreViewModel

  @Mock
  private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val listOfGenres = arrayListOf<Genre>()

  @Before
  fun setup() {
    viewModel = GenreViewModel(getMovieGenreUseCase, listOfGenres)
  }

  @Test
  fun `fetch movie genre successfully`() = runBlocking {
    val genre1 = Genre(1, "Cartoon")
    val genre2 = Genre(2, "Drama")
    val genre3 = Genre(3, "Horror")

    val genres = listOf(genre1, genre2, genre3)
    val getMovieGenreResult = GetMovieGenreResult(genres)
    val result = UseCaseResultWrapper.Success(getMovieGenreResult)

    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfGenres.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie genre`() = runBlocking {
    val msg = "error fetch genre"
    val result = UseCaseResultWrapper.ErrorResult(msg)

    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
