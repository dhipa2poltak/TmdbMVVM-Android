package com.dpfht.tmdbmvvm.domain.usecase

import com.dpfht.tmdbmvvm.data.api.CallbackWrapper
import com.dpfht.tmdbmvvm.data.model.remote.Review
import com.dpfht.tmdbmvvm.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository,
  private val compositeDisposable: CompositeDisposable
): GetMovieReviewUseCase {

  override operator fun invoke(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieReviews(movieId, page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<ReviewResponse>() {
        override fun onSuccessCall(responseBody: ReviewResponse) {
          responseBody.results?.let {
            onSuccess(it, responseBody.page)
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
