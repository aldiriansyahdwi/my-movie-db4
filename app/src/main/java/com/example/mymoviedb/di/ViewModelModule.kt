package com.example.mymoviedb.di

import com.example.mymoviedb.ui.detail.DetailViewModel
import com.example.mymoviedb.ui.favorite.FavoriteViewModel
import com.example.mymoviedb.ui.home.HomeViewModel
import com.example.mymoviedb.ui.login.LoginViewModel
import com.example.mymoviedb.ui.profile.ProfileViewModel
import com.example.mymoviedb.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::HomeViewModel)

    viewModelOf(::ProfileViewModel)
    viewModelOf(::RegisterViewModel)

}