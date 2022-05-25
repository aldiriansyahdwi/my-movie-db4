package com.example.mymoviedb.data.userdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userFavorite")
class UserFavorite (
    @PrimaryKey (autoGenerate = true) var id: Int?,
    @ColumnInfo (name = "user_email") var userEmail: String?,
    @ColumnInfo (name = "movie_id") var movieId: Int,
    @ColumnInfo (name = "movie_title") var movieTitle: String,
    @ColumnInfo (name = "poster_path") var posterPath: String
)