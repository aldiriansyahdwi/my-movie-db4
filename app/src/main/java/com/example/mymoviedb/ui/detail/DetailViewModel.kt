package com.example.mymoviedb.ui.detail

import androidx.lifecycle.*
import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.repository.Repository
import com.example.mymoviedb.data.userdatabase.UserFavorite
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

    fun saveFavorite(favorite: UserFavorite) = viewModelScope.launch {
        _saved.value = repository.insertFavorite(favorite)
    }

    fun isFavorite(email: String, movieId: Int) = viewModelScope.launch {
        _favorite.value = repository.isFavorite(email, movieId)
    }
}