package com.example.mymoviedb.data.listmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviedb.databinding.MovieItemBinding

class ListMovieAdapter(private val onItemClick: OnClickListener): RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<Results>(){
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<Results>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(MovieItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListMovieAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let{holder.bind(data)}
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Results){
            binding.apply{
                tvMovieTitle.text = data.title
                tvMovieReleaseDate.text = data.releaseDate
                tvVoteAverage.text = data.voteAverage.toString()
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivMoviePoster)
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: Results)
    }

}