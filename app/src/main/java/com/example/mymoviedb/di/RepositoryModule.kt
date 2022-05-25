package com.example.mymoviedb.di

import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.datastore.UserDataStoreManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::Repository)
    singleOf(::UserDataStoreManager)

}