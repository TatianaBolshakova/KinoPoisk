package com.example.finalandroid.data.popularspagedmovielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.premiersmovielist.DiffUtilCallback
import com.example.finalandroid.data.premiersmovielist.MovieViewHolder
import com.example.finalandroid.databinding.MovieItemBinding


class MoviePagedListAdapter(
    private val onClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieViewHolder>(DiffUtilCallback()) {
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
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

