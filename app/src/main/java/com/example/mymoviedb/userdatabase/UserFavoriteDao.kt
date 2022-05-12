package com.example.mymoviedb.userdatabase

import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(userFavorite: UserFavorite): Long

    @Delete
    suspend fun deleteFavorite(userFavorite: UserFavorite): Int

    @Query("SELECT * FROM UserFavorite WHERE user_email LIKE :email")
    suspend fun getAllUserFavorite(email: String?): List<UserFavorite>

    @Query("SELECT * FROM UserFavorite WHERE user_email LIKE :email AND movie_id LIKE :movieId")
    suspend fun isFavorited(email: String, movieId: Int): List<UserFavorite>
}