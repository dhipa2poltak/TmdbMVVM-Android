package com.dpfht.tmdbmvvm.di

import com.dpfht.tmdbmvvm.BuildConfig
import com.dpfht.tmdbmvvm.Config
import com.dpfht.tmdbmvvm.data.api.RestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  fun provideBaseUrl(): String {
    return Config.API_BASE_URL
  }

  @Provides
  @Singleton
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
      .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
      .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
      .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
      .build()
  }

  @Provides
  @Singleton
  fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideClient(
    certificatePinner: CertificatePinner,
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    if (!httpClientBuilder.interceptors().contains(httpLoggingInterceptor) && BuildConfig.DEBUG) {
      httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    httpClientBuilder.addInterceptor(object : Interceptor {
      override fun intercept(chain: Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
          .addQueryParameter("api_key", Config.API_KEY)
          .build()

        val requestBuilder = original.newBuilder()
          .url(url)
          .method(original.method, original.body)

        val request = requestBuilder.build()

        return chain.proceed(request)
      }
    })

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }
}
