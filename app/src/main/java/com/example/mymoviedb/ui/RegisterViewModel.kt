package com.example.mymoviedb.ui

import androidx.lifecycle.ViewModel
import com.example.mymoviedb.repository.UserRepository
import com.example.mymoviedb.userdatabase.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {

    fun saveUser (user: User): Long = runBlocking {
        userRepository.insertUser(user)
    }
}