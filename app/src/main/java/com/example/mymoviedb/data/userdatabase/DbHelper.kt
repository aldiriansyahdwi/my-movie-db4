package com.example.mymoviedb.data.userdatabase

class DbHelper(private val userDao: UserDao, private val userFavoriteDao: UserFavoriteDao) {
    // user
    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun verifyLogin(email: String, password: String) = userDao.verifyLogin(email, password)

    suspend fun checkEmail(email: String) = userDao.checkEmail(email)

    // user favorite
    suspend fun insertFavorite(favorite: UserFavorite) = userFavoriteDao.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: UserFavorite) = userFavoriteDao.deleteFavorite(favorite)

    suspend fun getAllFavorite(email: String) = userFavoriteDao.getAllUserFavorite(email)

    suspend fun favorite(email: String, movieId: Int) = userFavoriteDao.isFavorited(email, movieId)
}