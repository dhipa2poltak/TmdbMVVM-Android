package com.dpfht.tmdbmvvm.di

import com.dpfht.tmdbmvvm.data.repository.AppRepository
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbmvvm.domain.usecase.GetMovieTrailerUseCaseImpl
import com.dpfht.tmdbmvvm.feature.movietrailer.MovieTrailerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Module
@InstallIn(SingletonComponent::class)
class YouTubePlayerModule {

  @Provides
  fun provideJob(): Job {
    return Job()
  }

  @Provides
  fun provideCoroutineScope(job: Job): CoroutineScope {
    return CoroutineScope(job)
  }

  @Provides
  fun provideGetMovieTrailerUseCase(appRepository: AppRepository): GetMovieTrailerUseCase {
    return GetMovieTrailerUseCaseImpl(appRepository)
  }

  @Provides
  fun provideYouTubePlayerViewModel(
    useCase: GetMovieTrailerUseCase,
    scope: CoroutineScope
  ): MovieTrailerViewModel {
    return MovieTrailerViewModel(useCase, scope)
  }
}
