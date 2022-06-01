package com.dpfht.tmdbmvvm.data.api

import com.dpfht.tmdbmvvm.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

abstract class CallbackWrapper<T>: Callback<T> {

  override fun onResponse(call: Call<T>, response: Response<T>) {
    if (response.isSuccessful) {
      val responseBody = response.body()
      if (responseBody != null) {
        onSuccessCall(responseBody)
      } else {
        onErrorCall("null response body")
      }
    } else {
      val errorResponse = ErrorUtil.parseApiError(response)
      onErrorCall(errorResponse.statusMessage ?: "")
    }
  }

  override fun onFailure(call: Call<T>, t: Throwable) {
    if (call.isCanceled) {
      onCancelCall()
    } else {
      onErrorCall(if (t is IOException) "error in connection" else "error in conversion")
    }
  }

  protected abstract fun onSuccessCall(responseBody: T)

  protected abstract fun onErrorCall(message: String)

  protected abstract fun onCancelCall()
}
