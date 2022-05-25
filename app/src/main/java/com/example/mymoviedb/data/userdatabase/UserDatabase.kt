package com.example.mymoviedb.data.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, UserFavorite::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun userFavoriteDao(): UserFavoriteDao
//    companion object {
//        private var INSTANCE: UserDatabase? = null
//
//        fun getInstance(context: Context): UserDatabase?{
//            if(INSTANCE == null){
//                synchronized(UserDatabase::class){
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        UserDatabase::class.java,
//                        "User.db").build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance(){
//            INSTANCE = null
//        }
//    }
}