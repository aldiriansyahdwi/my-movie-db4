package com.example.mymoviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedb.repository.MoviePopularRepository
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.service.ApiHelper
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val apiHelper: ApiHelper, private val pref: UserDataStoreManager) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(MoviePopularRepository(apiHelper), pref) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}