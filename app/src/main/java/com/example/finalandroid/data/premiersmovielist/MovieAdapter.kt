package com.example.finalandroid.data.premiersmovielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.MovieItemBinding


class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

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
        val item = data.getOrNull(position)
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

    override fun getItemCount(): Int = data.size
}


class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)