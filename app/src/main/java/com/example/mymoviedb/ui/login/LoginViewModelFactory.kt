package com.example.mymoviedb.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.repository.UserRepository
import com.example.mymoviedb.userdatabase.UserDatabase

class LoginViewModelFactory(private val userDb: UserDatabase?, private val pref: UserDataStoreManager) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return userDb?.let { UserRepository(it) }?.let { LoginViewModel(it, pref) } as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}