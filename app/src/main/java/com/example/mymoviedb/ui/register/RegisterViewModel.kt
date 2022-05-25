package com.example.mymoviedb.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel(private val repository: Repository): ViewModel() {
    private val _savedUser : MutableLiveData<Long> = MutableLiveData()
    val savedUser: LiveData<Long>
    get() = _savedUser
    fun saveUser (user: User) = viewModelScope.launch {
        _savedUser.value = repository.insertUser(user)
    }
}