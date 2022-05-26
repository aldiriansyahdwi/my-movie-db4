package com.example.mymoviedb

import android.app.Application
import com.example.mymoviedb.di.*

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RetrofitKoin : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RetrofitKoin)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    databaseModule,
                    dataStoreModule
                )
            )
        }
    }
}