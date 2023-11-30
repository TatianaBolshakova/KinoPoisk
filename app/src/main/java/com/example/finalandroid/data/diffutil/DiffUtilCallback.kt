package com.example.finalandroid.data.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.finalandroid.data.models.Movie

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        oldItem.kinopoiskId == newItem.kinopoiskId &&
                oldItem.filmId == newItem.filmId

        return true
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

}
