package com.example.mymoviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mymoviedb.repository.FavoriteRepository
import com.example.mymoviedb.repository.MovieDetailRepository
import com.example.mymoviedb.userdatabase.UserFavorite
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val movieDetailRepository: MovieDetailRepository,
                      private val favoriteRepository: FavoriteRepository): ViewModel() {

    fun getDetail(movieId : Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try{
            emit(Resource.success(data = movieDetailRepository.getDetailMovie(movieId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun saveFavorite(favorite: UserFavorite): Long = runBlocking {
        favoriteRepository.insertFavorite(favorite)
    }

    fun isFavorited(email: String, movieId: Int): List<UserFavorite> = runBlocking{
        favoriteRepository.isFavorited(email, movieId)
    }
}