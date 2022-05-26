package com.example.mymoviedb.ui.login

import androidx.lifecycle.*
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _user: MutableLiveData<List<User>> = MutableLiveData()
    val user: LiveData<List<User>> get() = _user

    fun verifyLogin(email: String, password: String) = viewModelScope.launch {
        _user.value = repository.verifyLogin(email, password)
    }

    fun saveDataStore(email: String, username: String) {
        viewModelScope.launch {
            repository.setUser(email, username)
        }
    }

    fun getEmail(): LiveData<String> {
        return repository.getEmail().asLiveData()
    }

    fun getUsername(): LiveData<String> {
        return repository.getUsername().asLiveData()
    }
}