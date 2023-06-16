package com.bangkit.purrfectaid.di

import com.bangkit.purrfectaid.data.remote.ApiMaps
import com.bangkit.purrfectaid.data.repository.MapRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.MapRepository
import com.bangkit.purrfectaid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Provides
    @Singleton
    fun provideApiMapInstance(loggingInterceptor: HttpLoggingInterceptor): ApiMaps =
        Retrofit.Builder()
            .baseUrl(Constants.MAP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build())
            .build()
            .create(ApiMaps::class.java)

    @Provides
    @Singleton
    fun provideMapRepository(api: ApiMaps) : MapRepository {
        return MapRepositoryImpl(api)
    }
}