package com.dpfht.tmdbmvvm.di

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieReviewUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

  @Provides
  fun provideCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
  }

  @Provides
  fun provideGetMovieGenreUseCase(appRepository: AppRepository): GetMovieGenreUseCase {
    return GetMovieGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieByGenreUseCase(appRepository: AppRepository, ): GetMovieByGenreUseCase {
    return GetMovieByGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieDetailsUseCase(appRepository: AppRepository): GetMovieDetailsUseCase {
    return GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieReviewUseCase(appRepository: AppRepository): GetMovieReviewUseCase {
    return GetMovieReviewUseCaseImpl(appRepository)
  }
}
