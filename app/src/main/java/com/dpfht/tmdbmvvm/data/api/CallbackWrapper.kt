package com.dpfht.tmdbmvvm.data.api

import com.dpfht.tmdbmvvm.util.ErrorUtil
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

abstract class CallbackWrapper<T : Any>: DisposableObserver<T>() {

  override fun onNext(responseBody: T) {
    onSuccessCall(responseBody)
  }

  override fun onError(e: Throwable) {
    when (e) {
      is HttpException -> {
        val response = e.response()
        val errorResponse = response?.let { ErrorUtil.parseApiError(it) }
        onErrorCall(errorResponse?.statusMessage ?: "")
      }
      is IOException -> {
        onErrorCall("error in connection")
      } else -> {
      onErrorCall("error in conversion")
    }
    }
  }

  override fun onComplete() {}

  protected abstract fun onSuccessCall(responseBody: T)

  protected abstract fun onErrorCall(message: String)

  protected abstract fun onCancelCall()
}
