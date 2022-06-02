package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Trailer
import com.dpfht.tmdbmvvm.data.model.remote.response.TrailerResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  private val compositeDisposable = CompositeDisposable()

  override operator fun invoke(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieTrailer(movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<TrailerResponse>() {
        override fun onSuccessCall(responseBody: TrailerResponse) {
          responseBody.results?.let {
            onSuccess(it)
          }
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

  override fun onDestroy() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
  }
}
