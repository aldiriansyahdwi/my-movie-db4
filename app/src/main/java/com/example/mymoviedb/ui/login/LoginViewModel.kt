package com.example.mymoviedb.ui.login

import androidx.lifecycle.*
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.repository.UserRepository
import com.example.mymoviedb.userdatabase.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel (private val userRepository: UserRepository, private val pref: UserDataStoreManager): ViewModel(){


    fun verifyLogin(email: String, password: String): List<User> = runBlocking {
        userRepository.verifyLogin(email, password)
    }

    fun saveDataStore(email: String, username: String){
        runBlocking {
            pref.setUser(email, username)
        }
    }

    fun getEmail(): LiveData<String>{
        return pref.getEmail().asLiveData()
    }

    fun getUsername(): LiveData<String>{
        return pref.getUsername().asLiveData()
    }
}