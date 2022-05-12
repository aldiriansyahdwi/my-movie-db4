package com.example.mymoviedb.service

import com.example.mymoviedb.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=c1b3f19406cb6d184bb0c2449305c065")
    suspend fun getPopularMovie(): GetPopularMovieResponse

    @GET("https://api.themoviedb.org/3/movie/{movie_id}?api_key=c1b3f19406cb6d184bb0c2449305c065")
    fun getDetailMovie(@Path("movie_id") movieId: Int): Call<GetDetailMovieResponse>
}