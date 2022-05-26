package com.example.mymoviedb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviedb.databinding.FragmentFavoriteBinding
import com.example.mymoviedb.data.listmovie.FavoriteAdapter
import com.example.mymoviedb.data.userdatabase.UserFavorite
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModel()
    private val args: FavoriteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavorite.layoutManager =
            LinearLayoutManager(this.requireActivity(), LinearLayoutManager.VERTICAL, false)

        viewModel.getFavorite(args.email)
        viewModel.favorite.observe(viewLifecycleOwner) {
            showList(it)
        }
    }

    private fun showList(data: List<UserFavorite>) {
        val adapter = FavoriteAdapter(object : FavoriteAdapter.OnClickListener {
            override fun onClickItem(data: UserFavorite) {
                val clickedToDetail = data.userEmail?.let {
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                        data.movieId,
                        it
                    )
                }
                if (clickedToDetail != null) {
                    findNavController().navigate(clickedToDetail)
                }
            }
        }, object : FavoriteAdapter.OnClickListener {
            override fun onClickItem(data: UserFavorite) {
                viewModel.deleteFavorite(data)
            }
        })
        adapter.submitData(data)
        binding.rvFavorite.adapter = adapter
    }
}