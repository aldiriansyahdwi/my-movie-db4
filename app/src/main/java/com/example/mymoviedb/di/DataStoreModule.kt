package com.example.mymoviedb.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.mymoviedb.data.datastore.UserDataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(UserDataStoreManager.DATASTORE_NAME)
        }
    }
}