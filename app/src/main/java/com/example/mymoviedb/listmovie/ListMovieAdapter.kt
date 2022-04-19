package com.example.mymoviedb.listmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviedb.databinding.MovieItemBinding

class ListMovieAdapter(private val onItemClick: OnClickListener): RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<Result>?) = differ.submitList(value)

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
        fun bind(data: Result){
            binding.apply{
                tvMovieTitle.text = data.title
                tvMovieReleaseDate.text = data.releaseDate
                tvVoteAverage.text = data.voteAverage.toString()
                root.setOnClickListener{

                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: Result)
    }

}