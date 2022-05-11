package com.example.mymoviedb.userdatabase

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(userFavorite: UserFavorite): Long

    @Delete
    fun deleteFavorite(userFavorite: UserFavorite): Int

    @Query("SELECT * FROM UserFavorite WHERE user_email LIKE :email")
    fun getAllFavorite(email: String?): List<UserFavorite>
}