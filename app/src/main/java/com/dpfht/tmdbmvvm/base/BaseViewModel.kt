package com.dpfht.tmdbmvvm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

  protected var mIsLoadingData = false

  protected val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean>
    get() = mIsShowDialogLoading

  protected val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String>
    get() = mErrorMessage

  private val mShowCanceledMessage = MutableLiveData<Boolean>()
  val showCanceledMessage: LiveData<Boolean>
    get() = mShowCanceledMessage

  fun isLoadingData() = mIsLoadingData

  abstract fun start()
}
