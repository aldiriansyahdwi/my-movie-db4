package com.example.mymoviedb.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymoviedb.databinding.FragmentDetailBinding
import com.example.mymoviedb.data.detailmovie.Genre
import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.detailmovie.ProductionCompany
import com.example.mymoviedb.data.userdatabase.UserFavorite
import com.example.mymoviedb.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

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

        isFavorite(ownerEmail, movieId)

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

    private fun isFavorite(email: String, id: Int){
        viewModel.isFavorite(email, id)
        viewModel.favorite.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.btnFavorite.isEnabled = false
                binding.btnFavorite.text = "Favorited"
            }
        }
    }

    private fun favoriteClicked(favorite: UserFavorite){
        binding.btnFavorite.setOnClickListener {
            viewModel.saveFavorite(favorite)
            viewModel.saved.observe(viewLifecycleOwner) {
                if (it != 0.toLong()) {
                    Toast.makeText(requireContext(), "added to favorite", Toast.LENGTH_SHORT).show()
                    favorite.userEmail?.let { it1 -> isFavorite(it1, favorite.movieId) }
                }
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