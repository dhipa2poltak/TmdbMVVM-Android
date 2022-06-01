package com.dpfht.tmdbmvvm

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
        Builder().detectAll()
          .penaltyLog()
          .build()
      )
      StrictMode.setVmPolicy(
        VmPolicy.Builder().detectAll()
          .penaltyLog()
          .build()
      )
    }
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  companion object {
    lateinit var instance: TheApplication
  }
}
