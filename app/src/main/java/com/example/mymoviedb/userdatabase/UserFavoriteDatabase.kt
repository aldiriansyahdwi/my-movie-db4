package com.example.mymoviedb.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 2)
abstract class UserFavoriteDatabase: RoomDatabase() {

    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        private var INSTANCE: UserFavoriteDatabase? = null

        fun getInstance(context: Context): UserFavoriteDatabase?{
            if(INSTANCE == null){
                synchronized(UserFavoriteDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavoriteDatabase::class.java,
                        "UserFavorite.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}