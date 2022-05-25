package com.example.mymoviedb.di

import androidx.room.Room
import com.example.mymoviedb.data.userdatabase.DbHelper
import com.example.mymoviedb.data.userdatabase.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }
    single {
        get<UserDatabase>().userDao()
    }
    single {
        get<UserDatabase>().userFavoriteDao()
    }
    singleOf(::DbHelper)
}