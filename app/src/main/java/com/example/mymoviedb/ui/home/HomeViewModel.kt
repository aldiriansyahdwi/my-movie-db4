package com.example.mymoviedb.ui.home

import androidx.lifecycle.*
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: Repository) : ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")

    fun fetchAllData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getMovie()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun getUsername(): LiveData<String> {
        return repository.getUsername().asLiveData()
    }

    fun getEmail(): LiveData<String> {
        return repository.getEmail().asLiveData()
    }

}