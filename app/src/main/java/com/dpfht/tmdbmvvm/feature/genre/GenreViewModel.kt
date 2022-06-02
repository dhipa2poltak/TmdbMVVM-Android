package com.dpfht.tmdbmvvm.feature.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.dpfht.tmdbmvvm.base.BaseViewModel
import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Genre
import com.dpfht.tmdbmvvm.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
  val getMovieGenreUseCase: GetMovieGenreUseCase,
  val genres: ArrayList<Genre>,
  val compositeDisposable: CompositeDisposable
) : BaseViewModel() {

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int>
    get() = _notifyItemInserted

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    mIsShowDialogLoading.postValue(true)

    val subs = getMovieGenreUseCase()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<GetMovieGenreResult>() {
        override fun onSuccessCall(result: GetMovieGenreResult) {
          onSuccess(result.genres)
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

  private fun onSuccess(genres: List<Genre>) {
    for (genre in genres) {
      this.genres.add(genre)
      _notifyItemInserted.postValue(this.genres.size - 1)
    }
    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mErrorMessage.postValue(message)
  }

  private fun onCancel() {
    mIsShowDialogLoading.postValue(false)
    mShowCanceledMessage.postValue(true)
  }

  fun getNavDirectionsOnClickGenreAt(position: Int): NavDirections {
    val genre = genres[position]

    return GenreFragmentDirections.actionGenreFragmentToMoviesByGenreFragment(
      genre.id, genre.name ?: ""
    )
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
