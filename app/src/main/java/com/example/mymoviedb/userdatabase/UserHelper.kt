package com.example.mymoviedb.userdatabase

class UserHelper(private val userDb: UserDatabase) {
        suspend fun insertUser(user: User) = userDb.userDao().insertUser(user)
}