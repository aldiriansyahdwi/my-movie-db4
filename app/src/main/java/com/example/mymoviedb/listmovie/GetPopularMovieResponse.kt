package com.example.mymoviedb.listmovie


import com.google.gson.annotations.SerializedName

data class GetPopularMovieResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Results>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)