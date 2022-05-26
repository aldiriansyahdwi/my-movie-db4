package com.example.mymoviedb.data.repository

import com.example.mymoviedb.data.datastore.UserDataStoreManager
import com.example.mymoviedb.data.service.ApiHelper
import com.example.mymoviedb.data.userdatabase.DbHelper
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.data.userdatabase.UserFavorite

class Repository(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper,
    private val dataStore: UserDataStoreManager
) {
    // detail movie
    suspend fun getDetailMovie(id: Int) = apiHelper.getDetailMovie(id)

    // list popular movie
    suspend fun getMovie() = apiHelper.getAllPopularMovie()

    // user
    suspend fun insertUser(user: User) = dbHelper.insertUser(user)

    suspend fun updateUser(user: User) = dbHelper.updateUser(user)

    suspend fun verifyLogin(email: String, password: String) = dbHelper.verifyLogin(email, password)

    suspend fun checkEmail(email: String) = dbHelper.checkEmail(email)

    // user favorite
    suspend fun insertFavorite(movieFavorite: UserFavorite) = dbHelper.insertFavorite(movieFavorite)

    suspend fun deleteFavorite(movieFavorite: UserFavorite) = dbHelper.deleteFavorite(movieFavorite)

    suspend fun getFavorite(email: String) = dbHelper.getAllFavorite(email)

    suspend fun isFavorite(email: String, movieId: Int) = dbHelper.favorite(email, movieId)

    // data store
    suspend fun setUser(email: String, username: String) = dataStore.setUser(email, username)

    fun getEmail() = dataStore.getEmail()

    fun getUsername() = dataStore.getUsername()

    suspend fun deleteUser() = dataStore.deleteUser()

}