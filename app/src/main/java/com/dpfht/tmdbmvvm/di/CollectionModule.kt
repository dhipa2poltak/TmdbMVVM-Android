package com.dpfht.tmdbmvvm.di

import com.dpfht.tmdbmvvm.data.model.Genre
import com.dpfht.tmdbmvvm.data.model.Movie
import com.dpfht.tmdbmvvm.data.model.Review
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CollectionModule {

  @Provides
  fun provideGenres(): ArrayList<Genre> {
    return arrayListOf()
  }

  @Provides
  fun provideMovies(): ArrayList<Movie> {
    return arrayListOf()
  }

  @Provides
  fun provideReviews(): ArrayList<Review> {
    return arrayListOf()
  }
}
