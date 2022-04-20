package com.example.mymoviedb.userdatabase

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User): Int

    @Query("SELECT * FROM User WHERE email LIKE :email AND password LIKE :password")
    fun verifyLogin(email: String, password: String): List<User>

    @Query("SELECT * FROM User where email LIKE :email")
    fun checkEmail(email: String?): List<User>
}