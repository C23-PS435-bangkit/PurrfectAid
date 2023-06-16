package com.bangkit.purrfectaid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bangkit.purrfectaid.data.repository.DataStoreRepositoryImpl
import com.bangkit.purrfectaid.domain.repository.DataStoreRepository
import com.bangkit.purrfectaid.utils.Constants.DATA_TOKEN
import com.bangkit.purrfectaid.utils.Constants.DATA_USER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yosua on 31/05/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    @Named("DataUser")
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile(DATA_USER) },
            migrations = listOf(SharedPreferencesMigration(context, DATA_USER))
        )
    }

    @Provides
    @Singleton
    @Named("DataToken")
    fun provideDataToken(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile(DATA_TOKEN) },
            migrations = listOf(SharedPreferencesMigration(context, DATA_TOKEN))
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @Named("DataUser") dataStore: DataStore<Preferences>,
        @Named("DataToken") dataToken: DataStore<Preferences>
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore, dataToken)
    }
}