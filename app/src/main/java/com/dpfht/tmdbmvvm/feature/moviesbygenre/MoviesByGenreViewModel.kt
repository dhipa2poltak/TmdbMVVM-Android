package com.dpfht.tmdbmvvm.feature.moviesbygenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.dpfht.tmdbmvvm.base.BaseViewModel
import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Movie
import com.dpfht.tmdbmvvm.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
  val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  val movies: ArrayList<Movie>,
  val compositeDisposable: CompositeDisposable
): BaseViewModel() {

  private var _genreId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int>
    get() = _notifyItemInserted

  override fun start() {
    if (_genreId != -1 && movies.isEmpty()) {
      getMoviesByGenre()
    }
  }

  fun setGenreId(genreId: Int) {
    this._genreId = genreId
  }

  fun getMoviesByGenre() {
    if (isEmptyNextResponse) return

    mIsShowDialogLoading.postValue(true)
    mIsLoadingData = true

    val subs = getMovieByGenreUseCase(_genreId, page + 1)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<GetMovieByGenreResult>() {
        override fun onSuccessCall(result: GetMovieByGenreResult) {
          onSuccess(result.movies, result.page)
        }

        override fun onErrorCall(message: String) {
          onError(message)
        }

        override fun onCancelCall() {
          onCancel()
        }
      })

    compositeDisposable.add(subs)
  }

  private fun onSuccess(movies: List<Movie>, page: Int) {
    if (movies.isNotEmpty()) {
      this.page = page

      for (movie in movies) {
        this.movies.add(movie)
        _notifyItemInserted.postValue(this.movies.size - 1)
      }
    } else {
      isEmptyNextResponse = true
    }

    mIsShowDialogLoading.postValue(false)
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mIsLoadingData = false
    mErrorMessage.postValue(message)
  }

  private fun onCancel() {
    mIsShowDialogLoading.postValue(false)
    mIsLoadingData = false
    mShowCanceledMessage.postValue(true)
  }

  fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections {
    val movie = movies[position]

    return MoviesByGenreFragmentDirections.actionMovieByGenreToMovieDetails(movie.id)
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
