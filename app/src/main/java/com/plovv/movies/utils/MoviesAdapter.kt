package com.plovv.movies.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plovv.movies.data.MovieModel
import com.plovv.movies.databinding.MovieListItemBinding

class MoviesAdapter(private val clickLister: MovieClickListener): ListAdapter<MovieModel, MoviesAdapter.ViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, clickLister)
    }

    //region "Helper classes"

    class MovieClickListener(val listener: (movieID: String) -> Unit) {
        fun onClick(id: String) = listener(id)
    }

    class ViewHolder(private val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return ViewHolder(binding)
            }
        }

        fun bind(movie: MovieModel, clickListener: MovieClickListener) {
            binding.movie = movie
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    class MoviesDiffCallback: DiffUtil.ItemCallback<MovieModel>() {

        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

    //endregion

}