package com.example.mymoviedb.repository

import com.example.mymoviedb.service.ApiHelper

class MovieDetailRepository(private val apiHelper: ApiHelper) {
    suspend fun getDetailMovie(id: Int) = apiHelper.getDetailMovie(id)
}