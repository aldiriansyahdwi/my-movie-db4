package com.example.mymoviedb.userdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserFavorite (
    @PrimaryKey (autoGenerate = true) var id: Int?,
    @ColumnInfo (name = "user_email") var userEmail: String?,
    @ColumnInfo (name = "movie_id") var movieId: Int
)