package com.example.mymoviedb.userdatabase

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Query("SELECT * FROM User WHERE email LIKE :email AND password LIKE :password")
    suspend fun verifyLogin(email: String, password: String): List<User>

    @Query("SELECT * FROM User where email LIKE :email")
    suspend fun checkEmail(email: String?): List<User>
}