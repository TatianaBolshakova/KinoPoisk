package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.SearchMovieViewHolder
import com.example.finalandroid.databinding.MovieItem2Binding



class SearchMovieAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<SearchMovieViewHolder>() {

    private var data: List<Movie?> = emptyList()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            MovieItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            titleText.text = item?.nameRu ?: "Название не указано"
            genresText.text = item?.genres?.joinToString { genre -> genre.genre }?:""
            countryText.text = item?.countries?.joinToString { country -> country.country }?:""
            ratingText.text = item?.ratingKinopoisk.toString()
            imageView.isClickable= false
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.posterUrlPreview)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .into(imageView)
            }

        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

