package com.example.mymoviedb.repository

import com.example.mymoviedb.userdatabase.UserFavorite
import com.example.mymoviedb.userdatabase.UserFavoriteDatabase

class FavoriteRepository(private val favoriteDb: UserFavoriteDatabase) {

    suspend fun insertFavorite(movieFavorite: UserFavorite) = favoriteDb.userFavoriteDao().insertFavorite(movieFavorite)

    suspend fun deleteFavorite(movieFavorite: UserFavorite) = favoriteDb.userFavoriteDao().deleteFavorite(movieFavorite)

    suspend fun getFavorite(email: String) = favoriteDb.userFavoriteDao().getAllUserFavorite(email)

    suspend fun isFavorited(email: String, movieId: Int) = favoriteDb.userFavoriteDao().isFavorited(email, movieId)
}