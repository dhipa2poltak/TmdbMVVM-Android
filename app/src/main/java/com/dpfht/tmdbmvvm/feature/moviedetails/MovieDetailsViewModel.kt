package com.dpfht.tmdbmvvm.feature.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.dpfht.tmdbmvvm.Config
import com.dpfht.tmdbmvvm.base.BaseViewModel
import com.dpfht.tmdbmvvm.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  val getMovieDetailsUseCase: GetMovieDetailsUseCase
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
    getMovieDetailsUseCase(
      _movieId, this::onSuccess, this::onError, this::onCancel
    )
  }

  private fun onSuccess(response: MovieDetailsResponse) {
    imageUrl = ""
    if (response.posterPath != null) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + response.posterPath
    }

    _movieId = response.id
    title = response.title ?: ""
    overview = response.overview ?: ""

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
}
