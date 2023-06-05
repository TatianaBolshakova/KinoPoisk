package com.example.finalandroid.data.premiersmovielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.MovieItemBinding

class MovieListAdapter : ListAdapter<Movie, MovieViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            titleText.text = item?.nameRu?: ""
            genresText.text = item?.genres?.get(0)?.genre ?: ""
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.posterUrl)
                    .into(imageView)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId


    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}
