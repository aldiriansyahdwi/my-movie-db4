package com.example.mymoviedb.repository

import com.example.mymoviedb.service.ApiHelper

class MoviePopularRepository(private val apiHelper: ApiHelper) {

    suspend fun getMovie() = apiHelper.getAllPopularMovie()
}