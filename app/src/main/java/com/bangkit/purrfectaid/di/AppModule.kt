package com.bangkit.purrfectaid.di

import com.bangkit.purrfectaid.data.remote.ApiPredict
import com.bangkit.purrfectaid.data.repository.PredictRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.PredictRepository
import com.bangkit.purrfectaid.utils.Constants.PREDICT_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Yosua on 12/06/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideLoggingInterceptor() = if (BuildConfig.DEBUG) {
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    } else {
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//    }

    @Provides
    fun provideApiPredictInstance(loggingInterceptor: HttpLoggingInterceptor) : ApiPredict =
        Retrofit.Builder()
            .baseUrl(PREDICT_URL)
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
}