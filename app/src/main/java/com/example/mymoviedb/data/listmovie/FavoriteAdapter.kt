package com.example.mymoviedb.data.listmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviedb.databinding.FavoriteItemBinding
import com.example.mymoviedb.data.userdatabase.UserFavorite

class FavoriteAdapter(private val onItemClick: OnClickListener, private val onDeleteClick: OnClickListener): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    interface OnClickListener{
        fun onClickItem(data: UserFavorite)
    }

    private val diffCallBack = object: DiffUtil.ItemCallback<UserFavorite>(){
        override fun areItemsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<UserFavorite>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(FavoriteItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: UserFavorite){
            binding.apply {
                tvMovieTitle.text = data.movieTitle
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivMoviePoster)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
                ivDelete.setOnClickListener {
                    onDeleteClick.onClickItem(data)
                }
            }
        }
    }
}