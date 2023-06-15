package com.bangkit.purrfectaid.di

import com.bangkit.purrfectaid.BuildConfig
import com.bangkit.purrfectaid.data.remote.ApiAuth
import com.bangkit.purrfectaid.data.repository.AuthRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.AuthRepository
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import com.bangkit.purrfectaid.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yosua on 31/05/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    fun provideApiAuthInstance(loggingInterceptor: HttpLoggingInterceptor): ApiAuth =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build())
            .build()
            .create(ApiAuth::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiAuth, dataStore: DataStoreRepository) : AuthRepository {
        return AuthRepositoryImpl(api, dataStore)
    }
}