package com.example.mymoviedb.data.service

import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.listmovie.GetPopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=c1b3f19406cb6d184bb0c2449305c065")
    suspend fun getPopularMovie(): GetPopularMovieResponse

    @GET("movie/{movie_id}?api_key=c1b3f19406cb6d184bb0c2449305c065")
    suspend fun getDetailMovie(@Path("movie_id") movieId: Int): GetDetailMovieResponse
}