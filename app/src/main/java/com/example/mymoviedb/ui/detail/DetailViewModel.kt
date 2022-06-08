package com.example.mymoviedb.ui.detail

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.UserFavorite
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _saved: MutableLiveData<Long> = MutableLiveData()
    val saved: MutableLiveData<Long> get() = _saved

    private val _favorite: MutableLiveData<List<UserFavorite>> = MutableLiveData()
    val favorite: MutableLiveData<List<UserFavorite>> get() = _favorite

    private val _detailMovie = MutableLiveData<GetDetailMovieResponse>()
    val detailMovie: LiveData<GetDetailMovieResponse>
        get() = _detailMovie

    fun fetchAllData(movieId: Int){
        viewModelScope.launch{
            _detailMovie.postValue(repository.getDetailMovie(movieId))
        }
    }
//    fun getDetail(movieId: Int) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(data = repository.getDetailMovie(movieId)))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
//        }
//    }

    fun saveFavorite(favorite: UserFavorite) = viewModelScope.launch {
        _saved.value = repository.insertFavorite(favorite)
    }

    fun isFavorite(email: String, movieId: Int) = viewModelScope.launch {
        _favorite.value = repository.isFavorite(email, movieId)
    }
}