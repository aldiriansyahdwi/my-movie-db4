package com.example.mymoviedb.service

import com.example.mymoviedb.listmovie.Result
import com.example.mymoviedb.listmovie.TrendMovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("trending/movie/day?api_key=c1b3f19406cb6d184bb0c2449305c065")
    fun getTrendingMovie(): Call<TrendMovieResponse>
}