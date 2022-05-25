package com.example.mymoviedb.data.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun getAllPopularMovie() = apiService.getPopularMovie()

    suspend fun getDetailMovie(id: Int) = apiService.getDetailMovie(id)
}