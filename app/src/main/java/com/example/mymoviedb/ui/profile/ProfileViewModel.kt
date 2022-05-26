package com.example.mymoviedb.ui.profile

import androidx.lifecycle.*
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    private val _updatedUser: MutableLiveData<Int> = MutableLiveData()
    val updatedUser: LiveData<Int> get() = _updatedUser

    private val _user: MutableLiveData<List<User>> = MutableLiveData()
    val user: LiveData<List<User>> get() = _user

    fun updateData(user: User) = viewModelScope.launch {
        _updatedUser.value = repository.updateUser(user)
    }

    fun checkEmail(email: String) = viewModelScope.launch {
        _user.value = repository.checkEmail(email)
    }

    fun getEmail(): LiveData<String> {
        return repository.getEmail().asLiveData()
    }

    fun saveDataStore(email: String, username: String) {
        viewModelScope.launch {
            repository.setUser(email, username)
        }
    }

    fun deleteLogin() {
        runBlocking {
            repository.deleteUser()
        }
    }
}