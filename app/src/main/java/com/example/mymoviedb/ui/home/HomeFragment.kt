package com.example.mymoviedb.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentHomeBinding
import com.example.mymoviedb.listmovie.GetPopularMovieResponse
import com.example.mymoviedb.listmovie.ListMovieAdapter
import com.example.mymoviedb.listmovie.Results
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.service.ApiClient
import com.example.mymoviedb.service.ApiHelper
import com.example.mymoviedb.utils.Status

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get()= _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovieList.layoutManager = LinearLayoutManager(this.requireActivity(), LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this,
            HomeViewModelFactory(
                ApiHelper(ApiClient.instance),
                UserDataStoreManager(this.requireContext())))[HomeViewModel::class.java]

        viewModel.apply{
            getUsername().observe(viewLifecycleOwner){
                binding.tvWelcomeUser.text = it
            }
        }

        viewModel.fetchAllData().observe(viewLifecycleOwner) { resources ->
            when (resources.status){
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    viewModel.getEmail().observe(viewLifecycleOwner){
                        showList(resources.data, it)
                    }

                }
                Status.ERROR -> {

                }
            }
        }

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding.ivFavorite.setOnClickListener {
            viewModel.getEmail().observe(viewLifecycleOwner) {
                val emailFavorite = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment(it)
                findNavController().navigate(emailFavorite)

            }
        }
    }

    private fun showList(data: GetPopularMovieResponse?, owner: String){
        val adapter = ListMovieAdapter(object : ListMovieAdapter.OnClickListener{
            override fun onClickItem(data: Results) {
                val movieId = data.id
                val clickedToDetail = movieId?.let { HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,
                    owner
                ) }
                clickedToDetail?.let { findNavController().navigate(it) }
            }
        })
        adapter.submitData(data?.results)
        binding.rvMovieList.adapter = adapter
    }
}