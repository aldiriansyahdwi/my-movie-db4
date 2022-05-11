package com.example.mymoviedb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedb.repository.UserRepository
import com.example.mymoviedb.userdatabase.UserDatabase

class RegisterViewModelFactory(private val userDb: UserDatabase?) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return userDb?.let { UserRepository(it) }?.let { RegisterViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}