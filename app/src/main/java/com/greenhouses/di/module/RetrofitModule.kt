package com.greenhouses.di.module

import com.greenhouses.data.GreenhousesApi
import com.greenhouses.domain.repository.InterceptorRepository
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    private const val BASE_URL = "https://plannerok.ru/"

    @Singleton
    @Provides
    fun provideHttpClient(
        interceptorRepository: InterceptorRepository,
        authenticator: Authenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                interceptorRepository.addInterceptorToken(chain)
            }
            .authenticator(authenticator)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GreenhousesApi =
        retrofit.create(GreenhousesApi::class.java)
}
