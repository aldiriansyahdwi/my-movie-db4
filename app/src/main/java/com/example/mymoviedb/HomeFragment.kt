package com.example.mymoviedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviedb.databinding.FragmentHomeBinding
import com.example.mymoviedb.listmovie.ListMovieAdapter
import com.example.mymoviedb.listmovie.Result
import com.example.mymoviedb.listmovie.TrendMovieResponse
import com.example.mymoviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get()= _binding!!

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
        fetchAllData()
    }

    private fun fetchAllData(){
        ApiClient.instance.getTrendingMovie()
            .enqueue(object: Callback<TrendMovieResponse>{
                override fun onResponse(call: Call<TrendMovieResponse>, response: Response<TrendMovieResponse>) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        showList(body?.results)
                        Log.d("response-code", code.toString())
                    }
                    else{
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                        Log.d("response-code", code.toString())
                    }
                }

                override fun onFailure(call: Call<TrendMovieResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showList(data: List<Result>?){
        val adapter = ListMovieAdapter(object : ListMovieAdapter.OnClickListener{
            override fun onClickItem(data: Result) {

            }
        })
        adapter.submitData(data)
        binding.rvMovieList.adapter = adapter
    }
}