package com.bangkit.purrfectaid.di

import com.bangkit.purrfectaid.data.remote.ApiCommunity
import com.bangkit.purrfectaid.data.remote.ApiPredict
import com.bangkit.purrfectaid.data.repository.CommunityRepositoryImpl
import com.bangkit.purrfectaid.data.repository.PredictRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.CommunityRepository
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import com.bangkit.purrfectaid.domain.repository.PredictRepository
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
import javax.inject.Singleton

/**
 * Created by Yosua on 12/06/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiPredictInstance(loggingInterceptor: HttpLoggingInterceptor) : ApiPredict =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            ).build()
            .create(ApiPredict::class.java)

    @Provides
    fun providePredictRepository(api: ApiPredict) : PredictRepository {
        return PredictRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(dataStore: DataStoreRepository) = Interceptor { chain ->
        var token: String
        runBlocking {
            withContext(Dispatchers.Default) {
                token = dataStore.getToken()!!
            }
        }

        val requestHeaders = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        chain.proceed(requestHeaders)
    }

    @Provides
    @Singleton
    fun provideApiCommunityInstance(loggingInterceptor: HttpLoggingInterceptor, tokenInterceptor: Interceptor) : ApiCommunity =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(tokenInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            ).build()
            .create(ApiCommunity::class.java)

    @Provides
    @Singleton
    fun provideCommunityRepository(api: ApiCommunity) : CommunityRepository {
        return CommunityRepositoryImpl(api)
    }
}