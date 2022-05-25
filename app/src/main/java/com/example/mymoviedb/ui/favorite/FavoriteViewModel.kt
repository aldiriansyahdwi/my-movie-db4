package com.example.mymoviedb.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.data.userdatabase.UserFavorite
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteViewModel (private val repository: Repository): ViewModel(){
    private val _favorite : MutableLiveData<List<UserFavorite>> = MutableLiveData()
    val favorite : MutableLiveData<List<UserFavorite>> get() = _favorite

    private val _deleted : MutableLiveData<Int> = MutableLiveData()
    val deleted : MutableLiveData<Int> get() = _deleted

    fun getFavorite(email: String) = viewModelScope.launch {
        _favorite.value = repository.getFavorite(email)
    }

    fun deleteFavorite(favorite: UserFavorite) = viewModelScope.launch {
        _deleted.value = repository.deleteFavorite(favorite)
    }
}