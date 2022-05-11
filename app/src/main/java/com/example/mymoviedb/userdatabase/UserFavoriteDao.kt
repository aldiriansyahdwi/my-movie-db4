package com.example.mymoviedb.userdatabase

import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(userFavorite: UserFavorite): Long

    @Delete
    fun deleteFavorite(userFavorite: UserFavorite): Int

    @Query("SELECT * FROM UserFavorite WHERE user_email LIKE :email")
    fun getAllUserFavorite(email: String?): List<UserFavorite>
}