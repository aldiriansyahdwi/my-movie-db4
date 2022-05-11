package com.example.mymoviedb.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import com.example.mymoviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _listMovie: MutableLiveData<GetPopularMovieResponse?> = MutableLiveData()
    val listMovie: MutableLiveData<GetPopularMovieResponse?>
    get() = _listMovie

    fun fetchAllData(){
        ApiClient.instance.getPopularMovie()
            .enqueue(object: Callback<GetPopularMovieResponse> {
                override fun onResponse(call: Call<GetPopularMovieResponse>, response: Response<GetPopularMovieResponse>) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        _listMovie.postValue(body)
                        Log.d("response-code", code.toString())
                    }
                    else{
                        Log.d("response-code", code.toString())
                    }
                }
                override fun onFailure(call: Call<GetPopularMovieResponse>, t: Throwable) {}
            })
    }


}