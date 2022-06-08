package com.example.mymoviedb.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private val _savedUser: MutableLiveData<Long> = MutableLiveData()
    val savedUser: LiveData<Long>
        get() = _savedUser

    private val _emailInput = MutableStateFlow("")
    val emailInput = _emailInput.asStateFlow()

    private val _usernameInput = MutableStateFlow("")
    val usernameInput = _usernameInput.asStateFlow()

    private val _passwordInput = MutableStateFlow("")
    val passwordInput = _passwordInput.asStateFlow()

    private val _confirmPasswordInput = MutableStateFlow("")
    val confirmPasswordInput = _confirmPasswordInput.asStateFlow()

    fun setEmailInput(email: String){
        _emailInput.value = email
    }

    fun setUsernameInput(password: String){
        _usernameInput.value = password
    }

    fun setPasswordInput(password: String){
        _passwordInput.value = password
    }

    fun setConfirmPasswordInput(password: String){
        _confirmPasswordInput.value = password
    }

    fun saveUser(user: User) = viewModelScope.launch {
        _savedUser.value = repository.insertUser(user)
    }
}