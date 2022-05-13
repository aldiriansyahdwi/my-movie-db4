package com.example.mymoviedb.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymoviedb.databinding.FragmentDetailBinding
import com.example.mymoviedb.detailmovie.Genre
import com.example.mymoviedb.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.detailmovie.ProductionCompany
import com.example.mymoviedb.service.ApiClient
import com.example.mymoviedb.service.ApiHelper
import com.example.mymoviedb.userdatabase.UserFavorite
import com.example.mymoviedb.userdatabase.UserFavoriteDatabase
import com.example.mymoviedb.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

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
        val ownerEmail = args.emailUser
        viewModel = ViewModelProvider(this,
        DetailViewModelFactory(ApiHelper(ApiClient.instance),
        UserFavoriteDatabase.getInstance(this.requireContext())))[DetailViewModel::class.java]

        isFavorited(ownerEmail, movieId)

        viewModel.getDetail(movieId).observe(viewLifecycleOwner) {resources ->
            when (resources.status){
                Status.LOADING -> {

                }
                Status.SUCCESS ->{

                    resources.data?.title?.let {
                        resources.data.posterPath?.let { it1 ->
                            UserFavorite(null, ownerEmail, movieId,
                                it, it1
                            )
                        }
                    }?.let { favoriteClicked(it) }
                    resources.data?.let { showDetail(it) }
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun isFavorited(email: String, id: Int){
        if (viewModel.isFavorited(email, id).isNotEmpty()){
            binding.btnFavorite.isEnabled = false
            binding.btnFavorite.text = "Favorited"
        }
    }

    private fun favoriteClicked(favorite: UserFavorite){
        binding.btnFavorite.setOnClickListener {
            val addFavorite = viewModel.saveFavorite(favorite)
            if (addFavorite != 0.toLong()){
                Toast.makeText(requireContext(), "added to favorite", Toast.LENGTH_SHORT).show()
                favorite.userEmail?.let { it1 -> isFavorited(it1, favorite.movieId) }
            }
        }
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