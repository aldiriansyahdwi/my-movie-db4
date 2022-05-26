package com.example.mymoviedb.data.userdatabase

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
//    @PrimaryKey(autoGenerate = true) var id: Int?,
    @PrimaryKey var email: String,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "password") var password: String?,
    @ColumnInfo(name = "real_name") var realName: String?,
    @ColumnInfo(name = "birth_date") var birthDate: String?,
    @ColumnInfo(name = "address") var address: String?,
    @ColumnInfo(name = "profile") var profilePhoto: Bitmap

)