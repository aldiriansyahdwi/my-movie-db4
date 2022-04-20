package com.example.mymoviedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymoviedb.databinding.FragmentDetailBinding
import com.example.mymoviedb.detailmovie.Genre
import com.example.mymoviedb.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.detailmovie.ProductionCompany
import com.example.mymoviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        getDetail(movieId)
    }

    private fun getDetail(movieId : Int){
        ApiClient.instance.getDetailMovie(movieId)
            .enqueue(object: Callback<GetDetailMovieResponse> {
                override fun onResponse(
                    call: Call<GetDetailMovieResponse>,
                    response: Response<GetDetailMovieResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        body?.let { showDetail(it) }
                        Log.d("response-detail", code.toString())
                    }
                    else{
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                        Log.d("response-detail", code.toString())
                    }
                }

                override fun onFailure(call: Call<GetDetailMovieResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showDetail(data : GetDetailMovieResponse){
        binding.apply {
                Glide.with(this@DetailFragment)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivMoviePoster)
            tvMovieTitle.text = data.title
            tvMovieReleaseDate.text = data.releaseDate
            tvVoteAverage.text = data.voteAverage.toString()
            tvMovieDescription.text = data.overview
            tvTagline.text = data.tagline
            tvGenre.text = data.genres?.let { movieGenre(it) }
            tvProduction.text = data.productionCompanies?.let { movieProduction(it) }
        }
    }

    private fun movieGenre (listGenre : List<Genre>): String{
        var movieGenre = ""
        listGenre.forEach {
            if(movieGenre.isEmpty()){
                movieGenre = it.name.toString()
            }
            else movieGenre += ", ${it.name}"
        }
        return movieGenre
    }

    private fun movieProduction (listProduction : List<ProductionCompany>): String{
        var movieProduction = ""
        listProduction.forEach {
            if(movieProduction.isEmpty()){
                movieProduction = it.name.toString()
            }
            else movieProduction += ", ${it.name}"
        }
        return movieProduction
    }
}