package com.dpfht.tmdbmvvm.feature.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.dpfht.tmdbmvvm.Config
import com.dpfht.tmdbmvvm.base.BaseViewModel
import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  val compositeDisposable: CompositeDisposable
): BaseViewModel() {

  private var _movieId = -1
  private var title = ""
  private var overview = ""
  private var imageUrl = ""

  //--

  private val _titleData = MutableLiveData<String>()
  val titleData: LiveData<String>
    get() = _titleData

  private val _overviewData = MutableLiveData<String>()
  val overviewData: LiveData<String>
    get() = _overviewData

  private val _imageUrlData = MutableLiveData<String>()
  val imageUrlData: LiveData<String>
    get() = _imageUrlData

  //--

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun getMovieId(): Int {
    return _movieId
  }

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    } else {
      _titleData.postValue(title)
      _overviewData.postValue(overview)
      _imageUrlData.postValue(imageUrl)
    }
  }

  private fun getMovieDetails() {
    mIsShowDialogLoading.postValue(true)

    val subs = getMovieDetailsUseCase(_movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<GetMovieDetailsResult>() {
        override fun onSuccessCall(result: GetMovieDetailsResult) {
          onSuccess(result.movieId, result.title, result.overview, result.posterPath)
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

  private fun onSuccess(pId: Int, pTitle: String, pOverview: String, pPosterPath: String) {
    imageUrl = ""
    if (pPosterPath.isNotEmpty()) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + pPosterPath
    }

    _movieId = pId
    title = pTitle
    overview = pOverview

    _titleData.postValue(title)
    _overviewData.postValue(overview)
    _imageUrlData.postValue(imageUrl)

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

  fun getNavDirectionsToMovieReviews(): NavDirections {
    return MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(_movieId, title)
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
