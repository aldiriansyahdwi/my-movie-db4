package com.example.mymoviedb.service

import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular?api_key=c1b3f19406cb6d184bb0c2449305c065")
    fun getTrendingMovie(): Call<GetPopularMovieResponse>


}