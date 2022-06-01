package com.dpfht.tmdbmvvm.di

import com.dpfht.tmdbmvvm.data.api.RestService
import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideAppRepository(restService: RestService): AppRepository {
    return AppRepositoryImpl(restService)
  }
}
