package com.example.mymoviedb.repository

import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase
import com.example.mymoviedb.userdatabase.UserFavorite
import com.example.mymoviedb.userdatabase.UserFavoriteDatabase

class UserRepository(private val userDb: UserDatabase) {

    suspend fun insertUser(user: User) = userDb.userDao().insertUser(user)

    suspend fun updateUser(user: User) = userDb.userDao().updateUser(user)

    suspend fun verifyLogin(email: String, password: String) = userDb.userDao().verifyLogin(email, password)
}