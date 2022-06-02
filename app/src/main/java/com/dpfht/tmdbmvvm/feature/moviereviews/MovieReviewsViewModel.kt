package com.dpfht.tmdbmvvm.feature.moviereviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.tmdbmvvm.base.BaseViewModel
import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Review
import com.dpfht.tmdbmvvm.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
  val getMovieReviewUseCase: GetMovieReviewUseCase,
  val reviews: ArrayList<Review>,
  val compositeDisposable: CompositeDisposable
): BaseViewModel() {

  private var _movieId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int>
    get() = _notifyItemInserted

  override fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun getMovieReviews() {
    if (isEmptyNextResponse) return

    mIsShowDialogLoading.postValue(true)
    mIsLoadingData = true

    val subs = getMovieReviewUseCase(_movieId, page + 1)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<GetMovieReviewResult>() {
        override fun onSuccessCall(result: GetMovieReviewResult) {
          onSuccess(result.reviews, result.page)
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

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        _notifyItemInserted.postValue(this.reviews.size - 1)
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

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
