package com.dpfht.tmdbmvvm.feature.movietrailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Trailer
import com.dpfht.tmdbmvvm.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieTrailerUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Locale

class MovieTrailerViewModel(
  val getMovieTrailerUseCase: GetMovieTrailerUseCase
) {

  val compositeDisposable = CompositeDisposable()

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
    val subs = getMovieTrailerUseCase(_movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<GetMovieTrailerResult>() {
        override fun onSuccessCall(result: GetMovieTrailerResult) {
          onSuccess(result.trailers)
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

  fun onDestroy() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
  }
}
