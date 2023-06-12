package com.bangkit.purrfectaid.di

import com.bangkit.purrfectaid.data.remote.ApiAuth
import com.bangkit.purrfectaid.data.repository.AuthRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Yosua on 12/06/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiAuth) : AuthRepository {
        return AuthRepositoryImpl(api)
    }
}