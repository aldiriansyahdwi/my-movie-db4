package com.example.mymoviedb.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import com.example.mymoviedb.listmovie.ListMovieAdapter
import com.example.mymoviedb.listmovie.Results
import com.example.mymoviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val listMovie: MutableLiveData<GetPopularMovieResponse> = MutableLiveData()

    fun fetchAllData(){
        ApiClient.instance.getPopularMovie()
            .enqueue(object: Callback<GetPopularMovieResponse> {
                override fun onResponse(call: Call<GetPopularMovieResponse>, response: Response<GetPopularMovieResponse>) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        listMovie.postValue(body)
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