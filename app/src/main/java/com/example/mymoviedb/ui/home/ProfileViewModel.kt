package com.example.mymoviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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
}