package com.example.mymoviedb.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.repository.UserRepository
import com.example.mymoviedb.userdatabase.User
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val userRepository: UserRepository, private val pref: UserDataStoreManager): ViewModel() {

    fun updateData(user: User): Int = runBlocking {
        userRepository.updateUser(user)
    }

    fun checkEmail(email: String): List<User> = runBlocking {
        userRepository.checkEmail(email)
    }

    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }

    fun saveDataStore(email: String, username: String){
        runBlocking {
            pref.setUser(email, username)
        }
    }

    fun deleteLogin(){
        runBlocking {
            pref.deleteUser()
        }
    }


    fun getBitmap(image: Drawable, context: Context): Bitmap = runBlocking{
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(image)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        (result as BitmapDrawable).bitmap
    }
}