package com.example.mymoviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedb.repository.FavoriteRepository
import com.example.mymoviedb.repository.MovieDetailRepository
import com.example.mymoviedb.service.ApiHelper
import com.example.mymoviedb.userdatabase.UserFavoriteDatabase
import java.lang.IllegalArgumentException

class DetailViewModelFactory(private val apiHelper: ApiHelper,
                             private val favoriteDb: UserFavoriteDatabase?) :
ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return favoriteDb?.let { FavoriteRepository(it) }?.let {
                DetailViewModel(MovieDetailRepository(apiHelper),
                    it
                )
            } as T
        }
        throw IllegalArgumentException("unknown class name")
    }
}