package com.example.mymoviedb.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import com.example.mymoviedb.repository.MoviePopularRepository
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.service.ApiClient
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val moviePopularRepository: MoviePopularRepository, private val pref: UserDataStoreManager) : ViewModel() {
    val username : MutableLiveData<String> = MutableLiveData("")

    fun fetchAllData() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try{
            emit(Resource.success(data = moviePopularRepository.getMovie()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun getUsername(): LiveData<String>{
        return pref.getUsername().asLiveData()
    }

    fun getEmail(): LiveData<String>{
        return pref.getEmail().asLiveData()
    }

    fun deleteLogin(){
        runBlocking {
            pref.deleteUser()
        }
    }
}