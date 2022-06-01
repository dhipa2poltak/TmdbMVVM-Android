package com.dpfht.tmdbmvvm.feature.movietrailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.tmdbmvvm.data.model.Trailer
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieTrailerUseCase
import java.util.Locale

class MovieTrailerViewModel(
  val getMovieTrailerUseCase: GetMovieTrailerUseCase
) {

  private var _movieId = -1

  private val _keyVideo = MutableLiveData<String>()
  val keyVideo: LiveData<String>
    get() = _keyVideo

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String>
    get() = mErrorMessage

  private val mShowCanceledMessage = MutableLiveData<Boolean>()
  val showCanceledMessage: LiveData<Boolean>
    get() = mShowCanceledMessage

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    getMovieTrailerUseCase(
      _movieId, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(trailers: List<Trailer>) {
    var keyVideo = ""
    for (trailer in trailers) {
      if (trailer.site?.lowercase(Locale.ROOT)
          ?.trim() == "youtube"
      ) {
        keyVideo = trailer.key ?: ""
        break
      }
    }

    if (keyVideo.isNotEmpty()) {
      _keyVideo.postValue(keyVideo)
    }
  }

  fun onError(message: String) {
    mErrorMessage.postValue(message)
  }

  fun onCancel() {
    mShowCanceledMessage.postValue(true)
  }
}
