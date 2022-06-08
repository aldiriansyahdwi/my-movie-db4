package com.example.mymoviedb.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.mymoviedb.data.listmovie.Results
import com.example.mymoviedb.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    val listMovie: MutableState<List<Results>> = mutableStateOf(ArrayList())

    init {
        fetchAllData()
    }

    private fun fetchAllData() = viewModelScope.launch {
        val result = repository.getMovie()
        listMovie.value = result.results!!
    }

    fun getUsername(): LiveData<String> {
        return repository.getUsernamePref().asLiveData()
    }

    fun getEmail(): LiveData<String> {
        return repository.getEmailPref().asLiveData()
    }
}