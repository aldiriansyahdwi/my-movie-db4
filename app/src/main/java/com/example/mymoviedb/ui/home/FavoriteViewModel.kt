package com.example.mymoviedb.ui.home

import androidx.lifecycle.ViewModel
import com.example.mymoviedb.repository.FavoriteRepository
import com.example.mymoviedb.userdatabase.UserFavorite
import kotlinx.coroutines.runBlocking

class FavoriteViewModel (private val favoriteRepository: FavoriteRepository): ViewModel(){
    fun getFavorite(email: String) : List<UserFavorite> = runBlocking {
        favoriteRepository.getFavorite(email)
    }

    fun deleteFavorite(favorite: UserFavorite) : Int = runBlocking {
        favoriteRepository.deleteFavorite(favorite)
    }
}